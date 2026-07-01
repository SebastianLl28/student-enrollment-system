package pe.utp.marcodesarrolloweb.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.utp.marcodesarrolloweb.model.Enrollment;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;

/**
 * @author Alonso
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  boolean existsByStudentIdAndAcademicProgramIdAndAcademicPeriodIdAndStatusNot(
      Long studentId, Long academicProgramId, Long academicPeriodId, EnrollmentStatus status);

  long countByStatus(EnrollmentStatus status);

  boolean existsByAcademicPeriodId(Long academicPeriodId);

  @Query("SELECT e.academicProgram.name, COUNT(e) FROM Enrollment e GROUP BY e.academicProgram.name ORDER BY COUNT(e) DESC")
  List<Object[]> countEnrollmentsByProgram();
}
