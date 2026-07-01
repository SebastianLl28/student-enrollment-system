package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;

/**
 * @author Alonso
 */
@Entity
public class Enrollment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "studentId", insertable = false, updatable = false)
  private Student student;
  
  @ManyToOne
  @JoinColumn(name = "academic_program_id", insertable = false, updatable = false)
  private AcademicProgram academicProgram;
  
  @ManyToOne
  @JoinColumn(name = "academic_period_id", insertable = false, updatable = false)
  private AcademicPeriod academicPeriod;
  
  @Column(name = "studentId", nullable = false)
  private Long studentId;
  
  @Column(name = "academic_program_id", nullable = false)
  private Long academicProgramId;
  
  @Column(name = "academic_period_id", nullable = false)
  private Long academicPeriodId;
  
  @Column(nullable = false)
  private LocalDate enrollmentDate;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EnrollmentStatus status = EnrollmentStatus.PENDING_PAYMENT;
  
  private String observation;
  
  @Column(name = "paypal_order_id")
  private String paypalOrderId;
  
  @Column(precision = 10, scale = 2)
  private BigDecimal amount;
  
  @Column(length = 3)
  private String currency;
  
  @Column(name = "paid_at")
  private LocalDateTime paidAt;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  public Enrollment() {
  }
  
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  
  public Student getStudent() { return student; }
  public AcademicProgram getAcademicProgram() { return academicProgram; }
  public AcademicPeriod getAcademicPeriod() { return academicPeriod; }
  
  // ids (los que se setean al crear)
  public Long getStudentId() { return studentId; }
  public void setStudentId(Long studentId) { this.studentId = studentId; }
  
  public Long getAcademicProgramId() { return academicProgramId; }
  public void setAcademicProgramId(Long academicProgramId) { this.academicProgramId = academicProgramId; }
  
  public Long getAcademicPeriodId() { return academicPeriodId; }
  public void setAcademicPeriodId(Long academicPeriodId) { this.academicPeriodId = academicPeriodId; }
  
  public LocalDate getEnrollmentDate() { return enrollmentDate; }
  public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
  
  public EnrollmentStatus getStatus() { return status; }
  public void setStatus(EnrollmentStatus status) { this.status = status; }
  
  public String getObservation() { return observation; }
  public void setObservation(String observation) { this.observation = observation; }
  
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
  
  public void setStudent(Student student) {
    this.student = student;
  }
  
  public void setAcademicProgram(AcademicProgram academicProgram) {
    this.academicProgram = academicProgram;
  }
  
  public void setAcademicPeriod(AcademicPeriod academicPeriod) {
    this.academicPeriod = academicPeriod;
  }
  
  public String getPaypalOrderId() {
    return paypalOrderId;
  }
  
  public void setPaypalOrderId(String paypalOrderId) {
    this.paypalOrderId = paypalOrderId;
  }
  
  public BigDecimal getAmount() {
    return amount;
  }
  
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public String getCurrency() {
    return currency;
  }
  
  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  public LocalDateTime getPaidAt() {
    return paidAt;
  }
  
  public void setPaidAt(LocalDateTime paidAt) {
    this.paidAt = paidAt;
  }
}
