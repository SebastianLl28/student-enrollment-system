package pe.utp.marcodesarrolloweb.configuration.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Alonso
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;

  public SecurityConfig(AuthenticationProvider authenticationProvider) {
    this.authenticationProvider = authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authenticationProvider(authenticationProvider)
        .authorizeHttpRequests(auth -> auth
            // Páginas accesibles por ambos roles
            .requestMatchers("/app/secure/home", "/app/secure/profile").hasAnyRole("ADMIN", "SECRETARY")
            // Secretaria: solo lectura de estudiantes y matrículas
            .requestMatchers(HttpMethod.GET, "/app/secure/student", "/app/secure/student/**").hasAnyRole("ADMIN", "SECRETARY")
            .requestMatchers(HttpMethod.GET, "/app/secure/enrollment", "/app/secure/enrollment/**").hasAnyRole("ADMIN", "SECRETARY")
            // Todo lo demás del panel: solo ADMIN
            .requestMatchers("/app/secure/**").hasRole("ADMIN")
            .anyRequest().permitAll()
        )
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/payments/paypal/**")
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/app/secure/home", true)
            .failureUrl("/login?error")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .exceptionHandling(ex -> ex
            .accessDeniedHandler((req, res, denied) ->
                res.sendRedirect(req.getContextPath() + "/app/secure/home"))
        );
    return http.build();
  }
}
