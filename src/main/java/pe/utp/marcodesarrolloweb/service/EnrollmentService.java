package pe.utp.marcodesarrolloweb.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.model.Enrollment;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;
import pe.utp.marcodesarrolloweb.repository.EnrollmentRepository;

/**
 * @author Alonso
 */
@Service
public class EnrollmentService {
  
  private final EnrollmentRepository enrollmentRepository;
  private final AcademicPeriodService academicPeriodService;
  private final MailService mailService;

  public EnrollmentService(EnrollmentRepository enrollmentRepository,
      AcademicPeriodService academicPeriodService,
      MailService mailService) {
    this.enrollmentRepository = enrollmentRepository;
    this.academicPeriodService = academicPeriodService;
    this.mailService = mailService;
  }
  
  public List<Enrollment> findAll() {
    return enrollmentRepository.findAll();
  }

  public Page<Enrollment> findPage(int page, int size) {
    return enrollmentRepository.findAll(
        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "enrollmentDate", "id")));
  }

  public Enrollment findById(Long id) {
    return enrollmentRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("Matrícula no encontrada: " + id));
  }
  
  public Enrollment create(Long studentId, Long programId, Long periodId, String observation) {
    
    AcademicPeriod period = academicPeriodService.findById(periodId);
    if (Boolean.FALSE.equals(period.getActive())) {
      throw new IllegalStateException("El periodo académico no admite nuevas matrículas.");
    }
    
    boolean duplicate = enrollmentRepository
        .existsByStudentIdAndAcademicProgramIdAndAcademicPeriodIdAndStatusNot(
            studentId, programId, periodId, EnrollmentStatus.CANCELLED);
    
    if (duplicate) {
      throw new IllegalStateException("El estudiante ya está matriculado en ese programa y periodo.");
    }
    
    Enrollment enrollment = new Enrollment();
    enrollment.setStudentId(studentId);
    enrollment.setAcademicProgramId(programId);
    enrollment.setAcademicPeriodId(periodId);
    enrollment.setEnrollmentDate(LocalDate.now());
    enrollment.setStatus(EnrollmentStatus.PENDING_PAYMENT);
    enrollment.setObservation(observation);
    
    return enrollmentRepository.save(enrollment);
  }
  
  
  @Transactional
  public void attachPayPalOrder(Long enrollmentId, String orderId,
      BigDecimal amount, String currency) {
    Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
        .orElseThrow(() -> new IllegalStateException("Enrollment no encontrado: " + enrollmentId));
    enrollment.setPaypalOrderId(orderId);
    enrollment.setAmount(amount);
    enrollment.setCurrency(currency);
    enrollmentRepository.save(enrollment);
  }
  
  
  @Transactional
  public void markAsPaid(Long enrollmentId) {
    Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
        .orElseThrow(() -> new IllegalStateException("Enrollment no encontrado: " + enrollmentId));
    if (enrollment.getStatus() == EnrollmentStatus.PAID) {
      return;
    }
    enrollment.setStatus(EnrollmentStatus.PAID);
    enrollment.setPaidAt(LocalDateTime.now());
    enrollmentRepository.save(enrollment);

    mailService.sendPaymentConfirmation(
        enrollment.getStudent().getEmail(),
        enrollment.getStudent().getFullName(),
        enrollment.getAcademicProgram().getName(),
        enrollment.getAcademicPeriod().getName(),
        enrollment.getAmount(),
        enrollment.getCurrency(),
        enrollment.getPaidAt());
  }

  @Transactional
  public void cancel(Long enrollmentId) {
    Enrollment enrollment = findById(enrollmentId);
    if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
      return;
    }
    enrollment.setStatus(EnrollmentStatus.CANCELLED);
    enrollmentRepository.save(enrollment);

    mailService.sendCancellationEmail(
        enrollment.getStudent().getEmail(),
        enrollment.getStudent().getFullName(),
        enrollment.getAcademicProgram().getName(),
        enrollment.getAcademicPeriod().getName());
  }
}
