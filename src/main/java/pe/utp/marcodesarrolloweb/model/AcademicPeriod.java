package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Alonso
 */
@Entity
public class AcademicPeriod {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;
  
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;
  
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;
  
  private Boolean active = Boolean.TRUE;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDate createdAt;
  
  public AcademicPeriod() {
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public LocalDate getStartDate() {
    return startDate;
  }
  
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
  
  public LocalDate getEndDate() {
    return endDate;
  }
  
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
  
  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }
  
  public LocalDate getCreatedAt() {
    return createdAt;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
  
}
