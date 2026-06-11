package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;

/**
 * @author Alonso
 */
public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, Long> {
  
  boolean existsByName(String name);
  
  boolean existsByNameAndIdNot(String name, Long id);
  
  
}
