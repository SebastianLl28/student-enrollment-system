package pe.utp.marcodesarrolloweb.configuration.paypal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "paypal")
public record PayPalProperties(
    String clientId,
    String clientSecret,
    String baseUrl
) {

}
