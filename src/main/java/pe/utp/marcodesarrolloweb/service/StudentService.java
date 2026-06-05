package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.StudentLocalModel;

@Service
public class StudentService {
  
  private final SampleDataStore dataStore;
  
  public StudentService(SampleDataStore dataStore) {
    this.dataStore = dataStore;
  }
  
  
  public List<StudentLocalModel> findAll() {
    return dataStore.studentLocalModels;
  }
  
  public StudentLocalModel findById(Long id) {
    return dataStore.studentLocalModels.stream()
      .filter(s -> s.getId().equals(id))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Student not found"));
  }
  
  public void update(StudentLocalModel studentLocalModel) {
    dataStore.studentLocalModels.removeIf(s -> s.getId().equals(studentLocalModel.getId()));
    dataStore.studentLocalModels.add(studentLocalModel);
  }
  
  public void save(StudentLocalModel studentLocalModel) {
    if (studentLocalModel.getId() == null) {
      studentLocalModel.setId(dataStore.studentLocalModels.stream()
        .mapToLong(StudentLocalModel::getId)
        .max()
        .orElse(0L) + 1);
    }
    dataStore.studentLocalModels.add(studentLocalModel);
  }
  
}
