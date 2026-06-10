package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.Student;
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
  
  public Student findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Student not found"));
  }
  
  public Student save(Student student) {
    return repository.save(student);
  }
}
