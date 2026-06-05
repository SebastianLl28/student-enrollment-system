package pe.utp.marcodesarrolloweb.configuration.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.utp.marcodesarrolloweb.model.User;
import pe.utp.marcodesarrolloweb.repository.UserRepository;

/**
 * @author Alonso
 */
@Configuration
public class DataSeeder {
  
  @Bean
  CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      String email = "u12345678@utp.eud.pe";
      if (userRepository.findByEmail(email).isEmpty()) {
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user);
        System.out.println("Usuario de prueba creado: " + email);
      }
    };
  }
}
