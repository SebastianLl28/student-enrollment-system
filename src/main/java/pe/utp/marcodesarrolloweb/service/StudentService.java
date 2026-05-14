package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.Student;

@Service
public class StudentService {
  
  private final SampleDataStore dataStore;
  
  public StudentService(SampleDataStore dataStore) {
    this.dataStore = dataStore;
  }
  
  
  public List<Student> findAll() {
    return dataStore.students;
  }
  
  public Student findById(Long id) {
    return dataStore.students.stream()
      .filter(s -> s.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Student not found"));
  }
  
  public void update(Student student) {
    dataStore.students.removeIf(s -> s.getId().equals(student.getId()));
    dataStore.students.add(student);
  }
  
  public void save(Student student) {
    if (student.getId() == null) {
      student.setId(dataStore.students.stream()
        .mapToLong(Student::getId)
        .max()
        .orElse(0L) + 1);
    }
    dataStore.students.add(student);
  }
  
}
