package pe.utp.marcodesarrolloweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MarcoDeDesarrolloWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarcoDeDesarrolloWebApplication.class, args);
    }
}
