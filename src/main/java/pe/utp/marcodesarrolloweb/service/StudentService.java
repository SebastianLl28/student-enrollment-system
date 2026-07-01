package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;
import pe.utp.marcodesarrolloweb.repository.StudentRepository;

@Service
public class StudentService {
  
  private final StudentRepository repository;
  
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }
  
  public List<Student> findAll() {
    return repository.findAll();
  }

  public Page<Student> findPage(int page, int size) {
    return repository.findAll(PageRequest.of(page, size, Sort.by("lastName", "firstName")));
  }
  
  public Student findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Student not found"));
  }
  
  public Student save(Student student) {
    return repository.save(student);
  }
  
  public boolean isDuplicateDocument(DocumentType type, String number) {
    return repository.existsByDocumentTypeAndDocumentNumber(type, number);
  }
  
  public boolean isDuplicateDocument(DocumentType type, String number, Long excludeId) {
    return repository.existsByDocumentTypeAndDocumentNumberAndIdNot(type, number, excludeId);
  }

  public void toggleStatus(Long id) {
    Student student = findById(id);
    student.setStatus(student.getStatus() == StudentStatus.ACTIVE
        ? StudentStatus.INACTIVE : StudentStatus.ACTIVE);
    repository.save(student);
  }
}
