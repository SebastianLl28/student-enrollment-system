package pe.utp.marcodesarrolloweb.configuration.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Alonso
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(String baseUrl) {}
