package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  
  @ManyToOne(optional = false)
  private Student student;
  
  @ManyToOne(optional = false)
  private AcademicProgram academicProgram;
  
  @ManyToOne(optional = false)
  private AcademicPeriod academicPeriod;
  
  @Column(nullable = false)
  private LocalDate enrollmentDate;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EnrollmentStatus status = EnrollmentStatus.PENDING_PAYMENT;
  
  private String observation;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Student getStudent() {
    return student;
  }
  
  public void setStudent(Student student) {
    this.student = student;
  }
  
  public AcademicProgram getAcademicProgram() {
    return academicProgram;
  }
  
  public void setAcademicProgram(AcademicProgram academicProgram) {
    this.academicProgram = academicProgram;
  }
  
  public AcademicPeriod getAcademicPeriod() {
    return academicPeriod;
  }
  
  public void setAcademicPeriod(AcademicPeriod academicPeriod) {
    this.academicPeriod = academicPeriod;
  }
  
  public LocalDate getEnrollmentDate() {
    return enrollmentDate;
  }
  
  public void setEnrollmentDate(LocalDate enrollmentDate) {
    this.enrollmentDate = enrollmentDate;
  }
  
  public EnrollmentStatus getStatus() {
    return status;
  }
  
  public void setStatus(EnrollmentStatus status) {
    this.status = status;
  }
  
  public String getObservation() {
    return observation;
  }
  
  public void setObservation(String observation) {
    this.observation = observation;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
