package pe.utp.marcodesarrolloweb.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.utp.marcodesarrolloweb.service.EnrollmentService;
import pe.utp.marcodesarrolloweb.service.PayPalOrderService;

/**
 * @author Alonso
 */
@RestController
@RequestMapping("/payments/paypal")
public class PayPalWebhookController {
  
  private static final Logger log = LoggerFactory.getLogger(PayPalWebhookController.class);
  
  private final PayPalOrderService payPalOrderService;
  private final EnrollmentService enrollmentService;
  
  public PayPalWebhookController(PayPalOrderService payPalOrderService,
      EnrollmentService enrollmentService) {
    this.payPalOrderService = payPalOrderService;
    this.enrollmentService = enrollmentService;
  }
  
  @PostMapping("/webhook")
  public ResponseEntity<Void> webhook(@RequestBody JsonNode payload) {
    String eventType = payload.path("event_type").asText();
    JsonNode resource = payload.path("resource");
    log.info("Webhook PayPal recibido: {}", eventType);
    
    switch (eventType) {
      case "CHECKOUT.ORDER.APPROVED" -> {
        String orderId = resource.path("id").asText();
        // ojo: en este evento el custom_id va dentro de purchase_units[0]
        String customId = resource.path("purchase_units").path(0).path("custom_id").asText();
        boolean captured = payPalOrderService.captureOrder(orderId);
        log.info("Captura de orden {} -> completada: {}", orderId, captured);
        if (captured && !customId.isBlank()) {
          enrollmentService.markAsPaid(Long.valueOf(customId));
        }
      }
      case "PAYMENT.CAPTURE.COMPLETED" -> {
        // Captura confirmada → movemos el estado (fuente de verdad)
        String customId = resource.path("custom_id").asText();
        if (!customId.isBlank()) {
          enrollmentService.markAsPaid(Long.valueOf(customId));
        }
      }
      default -> log.debug("Evento ignorado: {}", eventType);
    }
    // Siempre 200 para que PayPal no reintente innecesariamente
    return ResponseEntity.ok().build();
  }
}
