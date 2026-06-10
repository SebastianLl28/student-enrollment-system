package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.Enrollment;

/**
 * @author Alonso
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
