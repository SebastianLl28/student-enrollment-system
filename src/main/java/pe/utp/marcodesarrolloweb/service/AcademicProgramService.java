package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;
import pe.utp.marcodesarrolloweb.repository.AcademicProgramRepository;

/**
 * @author Alonso
 */

@Service
public class AcademicProgramService {
  
  private final AcademicProgramRepository repository;
  
  public AcademicProgramService(AcademicProgramRepository repository) {
    this.repository = repository;
  }
  
  public List<AcademicProgram> findAll() {
    return repository.findAll();
  }
  
  public AcademicProgram findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Academic program not found"));
  }
  
  public AcademicProgram save(AcademicProgram academicProgram) {
    return repository.save(academicProgram);
  }
}
