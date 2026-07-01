package pe.utp.marcodesarrolloweb.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import pe.utp.marcodesarrolloweb.configuration.common.AppProperties;
import pe.utp.marcodesarrolloweb.configuration.common.CreatedOrder;
import pe.utp.marcodesarrolloweb.configuration.common.Link;
import pe.utp.marcodesarrolloweb.configuration.common.OrderResponse;

/**
 * @author Alonso
 */
@Service
public class PayPalOrderService {
  
  private final RestClient paypalRestClient;
  private final PayPalAuthService authService;
  private final AppProperties appProps;
  
  public PayPalOrderService(RestClient paypalRestClient,
      PayPalAuthService authService,
      AppProperties appProps) {
    this.paypalRestClient = paypalRestClient;
    this.authService = authService;
    this.appProps = appProps;
  }
  
  public CreatedOrder createOrder(Long enrollmentId, BigDecimal amount, String currency) {
    Map<String, Object> body = Map.of(
        "intent", "CAPTURE",
        "purchase_units", List.of(Map.of(
            "custom_id", String.valueOf(enrollmentId),
            "amount", Map.of(
                "currency_code", currency,
                "value", amount.setScale(2, RoundingMode.HALF_UP).toPlainString()
            )
        )),
        "payment_source", Map.of(
            "paypal", Map.of(
                "experience_context", Map.of(
                    "shipping_preference", "NO_SHIPPING",
                    "user_action", "PAY_NOW",
                    "return_url", appProps.baseUrl() + "/payments/paypal/return",
                    "cancel_url", appProps.baseUrl() + "/payments/paypal/cancel"
                )
            )
        )
    );
    
    OrderResponse resp = paypalRestClient.post()
        .uri("/v2/checkout/orders")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.getAccessToken())
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .body(OrderResponse.class);
    
    String approveUrl = resp.links().stream()
        .filter(l -> "payer-action".equals(l.rel()))
        .map(Link::href)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("PayPal no devolvió el link de pago"));
    
    return new CreatedOrder(resp.id(), approveUrl);
  }
  
  
  public boolean captureOrder(String orderId) {
    try {
      OrderResponse resp = paypalRestClient.post()
          .uri("/v2/checkout/orders/{id}/capture", orderId)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.getAccessToken())
          .contentType(MediaType.APPLICATION_JSON)
          .retrieve()
          .body(OrderResponse.class);
      return resp != null && "COMPLETED".equals(resp.status());
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().value() == 422
          && e.getResponseBodyAsString().contains("ORDER_ALREADY_CAPTURED")) {
        return true;
      }
      throw e;
    }
  }
  
}
