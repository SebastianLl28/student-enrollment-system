package pe.utp.marcodesarrolloweb.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import pe.utp.marcodesarrolloweb.model.StudentLocalModel;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;

@Component
public class SampleDataStore {
  
  public final StudentLocalModel studentLocalModelJuan = new StudentLocalModel(1L, "2026-001", "Juan", "Perez", DocumentType.DNI,
    "12345678", "juan.perez@email.com", "987654321", "Av. Lima 123", StudentStatus.ACTIVE,
    LocalDateTime.of(2026, 2, 1, 9, 0));
  
  public final StudentLocalModel studentLocalModelAna = new StudentLocalModel(2L, "2026-002", "Ana", "Torres", DocumentType.DNI,
    "87654321", "ana.torres@email.com", "912345678", "Jr. Miraflores 456", StudentStatus.ACTIVE,
    LocalDateTime.of(2026, 2, 5, 10, 0));
  
  public final StudentLocalModel studentLocalModelLuis = new StudentLocalModel(3L, "2025-099", "Luis", "Gomez",
    DocumentType.PASSPORT, "P1234567", "luis.gomez@email.com", "911111111", "Calle Arequipa 789",
    StudentStatus.INACTIVE, LocalDateTime.of(2025, 3, 10, 8, 0));
  
  public List<StudentLocalModel> studentLocalModels = new ArrayList<>(List.of(studentLocalModelJuan,
      studentLocalModelAna, studentLocalModelLuis));
  
}
