package pe.utp.marcodesarrolloweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;

/**
 * @author Alonso
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
  
  boolean existsByCode(String code);
  
  boolean existsByDocumentTypeAndDocumentNumber(DocumentType documentType, String documentNumber);
  
  boolean existsByDocumentTypeAndDocumentNumberAndIdNot(DocumentType documentType, String documentNumber, Long id);
}
