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
import pe.utp.marcodesarrolloweb.model.enums.UserRole;
import pe.utp.marcodesarrolloweb.model.enums.UserStatus;

/**
 * @author Alonso
 */
@Entity
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String firstName;
  
  @Column(nullable = false)
  private String lastName;
  
  @Column(nullable = false)
  private String email;
  
  @Column(nullable = false)
  private String password;
  
  @Column(nullable = false)
  private UserStatus userStatus = UserStatus.ACTIVE;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ADMIN'")
  private UserRole role = UserRole.ADMIN;
  
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;
  public User() {
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
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
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public UserStatus getUserStatus() {
    return userStatus;
  }
  
  public void setUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }
}
