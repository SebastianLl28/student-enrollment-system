package pe.utp.marcodesarrolloweb.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.User;

/**
 * @author Alonso
 */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
