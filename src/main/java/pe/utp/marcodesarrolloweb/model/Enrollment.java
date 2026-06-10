package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Alonso
 */
@Entity
public class Enrollment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private Long studentId;
  
  private Long academicProgramId;
  
  private Long academicPeriodId;
  
  private LocalDate enrollmentDate;
  
  private Boolean active = Boolean.TRUE;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDate createdAt;
  
  public Enrollment() {
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getStudentId() {
    return studentId;
  }
  
  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }
  
  public Long getAcademicProgramId() {
    return academicProgramId;
  }
  
  public void setAcademicProgramId(Long academicProgramId) {
    this.academicProgramId = academicProgramId;
  }
  
  public Long getAcademicPeriodId() {
    return academicPeriodId;
  }
  
  public void setAcademicPeriodId(Long academicPeriodId) {
    this.academicPeriodId = academicPeriodId;
  }
  
  public LocalDate getEnrollmentDate() {
    return enrollmentDate;
  }
  
  public void setEnrollmentDate(LocalDate enrollmentDate) {
    this.enrollmentDate = enrollmentDate;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
}
