package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

  public Page<AcademicProgram> findPage(int page, int size) {
    return repository.findAll(PageRequest.of(page, size, Sort.by("name")));
  }
  
  public AcademicProgram findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Academic program not found"));
  }
  
  public AcademicProgram save(AcademicProgram academicProgram) {
    return repository.save(academicProgram);
  }
  
  public boolean isDuplicateCode(String code) {
    return repository.existsByCode(code);
  }
  
  public boolean isDuplicateCode(String code, Long excludeId) {
    return repository.existsByCodeAndIdNot(code, excludeId);
  }

  public void toggleActive(Long id) {
    AcademicProgram program = findById(id);
    program.setActive(!program.getActive());
    repository.save(program);
  }
}
