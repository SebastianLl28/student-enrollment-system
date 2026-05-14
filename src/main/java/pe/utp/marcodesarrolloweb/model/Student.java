package pe.utp.marcodesarrolloweb.model;

import java.time.LocalDateTime;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;

public class Student {
  
  private Long id;
  private String code;
  private String firstName;
  private String lastName;
  private DocumentType documentType;
  private String documentNumber;
  private String email;
  private String phone;
  private String address;
  private StudentStatus status;
  private LocalDateTime createdAt;
  
  public Student() {
  }
  
  public Student(Long id, String code, String firstName, String lastName, DocumentType documentType,
    String documentNumber, String email, String phone, String address, StudentStatus status,
    LocalDateTime createdAt) {
    this.id = id;
    this.code = code;
    this.firstName = firstName;
    this.lastName = lastName;
    this.documentType = documentType;
    this.documentNumber = documentNumber;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.status = status;
    this.createdAt = createdAt;
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
