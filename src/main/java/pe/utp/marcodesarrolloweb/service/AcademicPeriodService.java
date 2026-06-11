package pe.utp.marcodesarrolloweb.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.repository.AcademicPeriodRepository;

/**
 * @author Alonso
 */
@Service
public class AcademicPeriodService {
  
  private final AcademicPeriodRepository repository;
  
  public AcademicPeriodService(AcademicPeriodRepository repository) {
    this.repository = repository;
  }
  
  public List<AcademicPeriod> findAll() {
    return repository.findAll();
  }
  
  public AcademicPeriod findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Academic period not found"));
  }
  
  public AcademicPeriod save(AcademicPeriod academicPeriod) {
    return repository.save(academicPeriod);
  }
  
  public boolean isDuplicateName(String name) {
    return repository.existsByName(name);
  }
  
  public boolean isDuplicateName(String name, Long excludeId) {
    return repository.existsByNameAndIdNot(name, excludeId);
  }
}
