package pe.utp.marcodesarrolloweb.service;

import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pe.utp.marcodesarrolloweb.configuration.paypal.PayPalProperties;
import pe.utp.marcodesarrolloweb.dto.TokenResponse;

/**
 * @author Alonso
 */
@Service
public class PayPalAuthService {
  
  private final RestClient paypalRestClient;
  private final PayPalProperties props;
  
  private String cachedToken;
  private Instant expiresAt = Instant.EPOCH;
  
  public PayPalAuthService(RestClient paypalRestClient, PayPalProperties props) {
    this.paypalRestClient = paypalRestClient;
    this.props = props;
  }
  
  public synchronized String getAccessToken() {
    if (cachedToken != null && Instant.now().isBefore(expiresAt.minusSeconds(60))) {
      return cachedToken;
    }
    
    TokenResponse resp = paypalRestClient.post()
        .uri("/v1/oauth2/token")
        .headers(h -> h.setBasicAuth(props.clientId(), props.clientSecret()))
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body("grant_type=client_credentials")
        .retrieve()
        .body(TokenResponse.class);
    
    this.cachedToken = resp.accessToken();
    this.expiresAt = Instant.now().plusSeconds(resp.expiresIn());
    return cachedToken;
  }

}
