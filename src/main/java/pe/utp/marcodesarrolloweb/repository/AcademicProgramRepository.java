package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;

/**
 * @author Alonso
 */
public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Long> {

  boolean existsByCode(String code);

  boolean existsByCodeAndIdNot(String code, Long id);

  long countByActiveTrue();
}
