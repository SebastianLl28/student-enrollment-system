package pe.utp.marcodesarrolloweb.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.repository.AcademicPeriodRepository;
import pe.utp.marcodesarrolloweb.repository.EnrollmentRepository;

/**
 * @author Alonso
 */
@Service
public class AcademicPeriodService {

  private final AcademicPeriodRepository repository;
  private final EnrollmentRepository enrollmentRepository;

  public AcademicPeriodService(AcademicPeriodRepository repository,
      EnrollmentRepository enrollmentRepository) {
    this.repository = repository;
    this.enrollmentRepository = enrollmentRepository;
  }

  public List<AcademicPeriod> findAll() {
    return repository.findAll();
  }

  public Page<AcademicPeriod> findPage(int page, int size) {
    return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startDate")));
  }

  public AcademicPeriod findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Periodo académico no encontrado"));
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

  public boolean hasOverlap(LocalDate startDate, LocalDate endDate) {
    return repository.countOverlapping(startDate, endDate) > 0;
  }

  public boolean hasOverlap(LocalDate startDate, LocalDate endDate, Long excludeId) {
    return repository.countOverlappingExcluding(startDate, endDate, excludeId) > 0;
  }

  public void delete(Long id) {
    if (enrollmentRepository.existsByAcademicPeriodId(id)) {
      throw new IllegalStateException(
          "No se puede eliminar el periodo porque tiene matrículas asociadas.");
    }
    repository.deleteById(id);
  }
}
