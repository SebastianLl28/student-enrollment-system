package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Alonso
 */
@Entity
public class AcademicProgram {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank(message = "El código es obligatorio")
  private String code;
  
  @NotBlank(message = "El nombre es obligatorio")
  private String name;
  
  private String description;
  
  private Boolean active = Boolean.TRUE;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  public AcademicProgram() {
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public void setActive(Boolean active) {
    this.active = active;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
