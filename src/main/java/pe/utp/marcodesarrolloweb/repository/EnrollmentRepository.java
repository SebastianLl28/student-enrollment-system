package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.Enrollment;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;

/**
 * @author Alonso
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
  
  boolean existsByStudentIdAndAcademicProgramIdAndAcademicPeriodIdAndStatusNot(
      Long studentId, Long academicProgramId, Long academicPeriodId, EnrollmentStatus status);
  
  
}
