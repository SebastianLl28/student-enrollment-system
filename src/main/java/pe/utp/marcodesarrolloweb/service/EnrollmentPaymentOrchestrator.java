package pe.utp.marcodesarrolloweb.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.configuration.common.CreatedOrder;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;
import pe.utp.marcodesarrolloweb.model.Enrollment;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;

/**
 * @author Alonso
 */
@Service
public class EnrollmentPaymentOrchestrator {

  private final EnrollmentService enrollmentService;
  private final PayPalOrderService payPalOrderService;
  private final MailService mailService;
  private final StudentService studentService;
  private final AcademicProgramService academicProgramService;
  private final AcademicPeriodService academicPeriodService;

  public EnrollmentPaymentOrchestrator(EnrollmentService enrollmentService,
      PayPalOrderService payPalOrderService,
      MailService mailService,
      StudentService studentService,
      AcademicProgramService academicProgramService,
      AcademicPeriodService academicPeriodService) {
    this.enrollmentService = enrollmentService;
    this.payPalOrderService = payPalOrderService;
    this.mailService = mailService;
    this.studentService = studentService;
    this.academicProgramService = academicProgramService;
    this.academicPeriodService = academicPeriodService;
  }

  public Enrollment enroll(Long studentId, Long programId, Long periodId, String observation) {
    // 1. Validar que estudiante y programa estén activos antes de hacer nada
    Student student = studentService.findById(studentId);
    if (student.getStatus() != StudentStatus.ACTIVE) {
      throw new IllegalStateException(
          "El estudiante no está activo y no puede matricularse.");
    }

    AcademicProgram program = academicProgramService.findById(programId);
    if (!Boolean.TRUE.equals(program.getActive())) {
      throw new IllegalStateException(
          "El programa académico no está activo y no admite nuevas matrículas.");
    }

    AcademicPeriod period = academicPeriodService.findById(periodId);

    // 2. Crear matrícula (valida periodo activo + duplicado)
    Enrollment enrollment = enrollmentService.create(studentId, programId, periodId, observation);

    // 3. Crear orden PayPal (llamada externa, fuera de la transacción de BD)
    BigDecimal amount = program.getPrice();
    String currency = "USD";
    CreatedOrder order = payPalOrderService.createOrder(enrollment.getId(), amount, currency);

    // 4. Persistir orderId + monto en la matrícula
    enrollmentService.attachPayPalOrder(enrollment.getId(), order.orderId(), amount, currency);

    // 5. Enviar correo con el link de pago
    mailService.sendPaymentLink(
        student.getEmail(),
        student.getFullName(),
        program.getName(),
        period.getName(),
        amount,
        currency,
        order.approveUrl());

    return enrollment;
  }
}
