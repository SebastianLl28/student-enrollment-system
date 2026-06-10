package pe.utp.marcodesarrolloweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;

/**
 * @author Alonso
 */
@Entity
public class Student {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String code;
  
  @Column(nullable = false)
  private String firstName;
  
  @Column(nullable = false)
  private String lastName;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DocumentType documentType;
  
  @Column(nullable = false)
  private String documentNumber;
  
  @Column(nullable = false)
  private String email;
  
  private String phone;
  
  private String address;
  
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StudentStatus status = StudentStatus.ACTIVE;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
  
  public Student() {
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
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public DocumentType getDocumentType() {
    return documentType;
  }
  
  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }
  
  public String getDocumentNumber() {
    return documentNumber;
  }
  
  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public StudentStatus getStatus() {
    return status;
  }
  
  public void setStatus(StudentStatus status) {
    this.status = status;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
  public String getFullName() {
    return firstName + " " + lastName;
  }
}
