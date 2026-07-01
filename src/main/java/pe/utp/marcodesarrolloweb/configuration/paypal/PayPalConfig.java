package pe.utp.marcodesarrolloweb.configuration.paypal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author Alonso
 */
@Configuration
public class PayPalConfig {
  
  @Bean
  RestClient paypalRestClient(PayPalProperties props) {
    return RestClient.builder()
        .baseUrl(props.baseUrl())
        .build();
  }

}
