package pe.utp.marcodesarrolloweb.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;
import pe.utp.marcodesarrolloweb.model.Enrollment;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;
import pe.utp.marcodesarrolloweb.repository.EnrollmentRepository;

/**
 * @author Alonso
 */
@Service
public class EnrollmentService {
  
  private final EnrollmentRepository enrollmentRepository;
  private final StudentService studentService;
  private final AcademicProgramService academicProgramService;
  private final AcademicPeriodService academicPeriodService;
  
  public EnrollmentService(EnrollmentRepository enrollmentRepository,
      StudentService studentService,
      AcademicProgramService academicProgramService,
      AcademicPeriodService academicPeriodService) {
    this.enrollmentRepository = enrollmentRepository;
    this.studentService = studentService;
    this.academicProgramService = academicProgramService;
    this.academicPeriodService = academicPeriodService;
  }
  
  public List<Enrollment> findAll() {
    return enrollmentRepository.findAll();
  }
  
  public Enrollment create(Long studentId, Long programId, Long periodId, String observation) {
    Student student = studentService.findById(studentId);
    AcademicProgram program = academicProgramService.findById(programId);
    AcademicPeriod period = academicPeriodService.findById(periodId);
    
    if (Boolean.FALSE.equals(period.getActive())) {
      throw new IllegalStateException("El periodo académico no admite nuevas matrículas.");
    }
    
    boolean duplicate = enrollmentRepository
        .existsByStudentIdAndAcademicProgramIdAndAcademicPeriodIdAndStatusNot(
            studentId, programId, periodId, EnrollmentStatus.CANCELLED);
    
    if (duplicate) {
      throw new IllegalStateException(
          "El estudiante ya está matriculado en ese programa y periodo.");
    }
    
    Enrollment enrollment = new Enrollment();
    enrollment.setStudent(student);
    enrollment.setAcademicProgram(program);
    enrollment.setAcademicPeriod(period);
    enrollment.setEnrollmentDate(LocalDate.now());
    enrollment.setStatus(EnrollmentStatus.PENDING_PAYMENT);
    enrollment.setObservation(observation);
    
    return enrollmentRepository.save(enrollment);
    
  }

}
