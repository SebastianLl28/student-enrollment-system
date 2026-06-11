package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.Student;

/**
 * @author Alonso
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
  
  boolean existsByCode(String code);
  
}
