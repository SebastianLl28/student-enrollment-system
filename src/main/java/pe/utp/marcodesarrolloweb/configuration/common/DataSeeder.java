package pe.utp.marcodesarrolloweb.configuration.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.User;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;
import pe.utp.marcodesarrolloweb.model.enums.UserRole;
import pe.utp.marcodesarrolloweb.repository.AcademicPeriodRepository;
import pe.utp.marcodesarrolloweb.repository.AcademicProgramRepository;
import pe.utp.marcodesarrolloweb.repository.StudentRepository;
import pe.utp.marcodesarrolloweb.repository.UserRepository;

/**
 * @author Alonso
 */
@Configuration
public class DataSeeder {
  
  @Bean
  CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      // Administrador
      String adminEmail = "admin@horizonte.edu.pe";
      if (userRepository.findByEmail(adminEmail).isEmpty()) {
        User admin = new User();
        admin.setFirstName("Administrador");
        admin.setLastName("Sistema");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(UserRole.ADMIN);
        userRepository.save(admin);
      }

      // Secretaria
      String secEmail = "secretaria@horizonte.edu.pe";
      if (userRepository.findByEmail(secEmail).isEmpty()) {
        User sec = new User();
        sec.setFirstName("María");
        sec.setLastName("García");
        sec.setEmail(secEmail);
        sec.setPassword(passwordEncoder.encode("sec123"));
        sec.setRole(UserRole.SECRETARY);
        userRepository.save(sec);
      }

      // Actualizar usuarios existentes sin rol asignado
      userRepository.findAll().forEach(u -> {
        if (u.getRole() == null) {
          u.setRole(UserRole.ADMIN);
          userRepository.save(u);
        }
      });
    };
  }
  
  @Bean
  CommandLineRunner seedAcademicPrograms(AcademicProgramRepository repository) {
    return args -> {
      List<AcademicProgram> programs = List.of(
          program("ING-SOFT", "Ingeniería de Software",
              "Desarrollo de software, sistemas y aplicaciones.", new BigDecimal("1500.00")),
          program("ING-SIS", "Ingeniería de Sistemas",
              "Análisis, diseño y gestión de sistemas de información.", new BigDecimal("1400.00")),
          program("ADM-EMP", "Administración de Empresas",
              "Gestión empresarial, finanzas y operaciones.", new BigDecimal("1300.00")),
          program("CONT", "Contabilidad", "Contabilidad financiera, tributación y auditoría.", new BigDecimal("1200.00")),
          program("ENG", "Programa de Inglés", "Formación en idioma inglés en distintos niveles.", new BigDecimal("800.00")),
          program("PRE", "Programa Preuniversitario",
              "Preparación para el ingreso a la universidad.", new BigDecimal("1000.00"))
      );
      programs.forEach(p -> {
        if (!repository.existsByCode(p.getCode())) {
          repository.save(p);
        }
      });
    };
  }
  
  @Bean
  CommandLineRunner seedAcademicPeriods(AcademicPeriodRepository repository) {
    return args -> {
      List<AcademicPeriod> periods = List.of(
          period("2025-II", LocalDate.of(2025, 8, 1), LocalDate.of(2025, 12, 15), false),
          period("2026-I", LocalDate.of(2026, 3, 1), LocalDate.of(2026, 7, 15), true),
          period("2026-II", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 12, 15), true)
      );
      periods.forEach(p -> {
        if (!repository.existsByName(p.getName())) {
          repository.save(p);
        }
      });
    };
  }
  
  @Bean
  CommandLineRunner seedStudents(StudentRepository repository) {
    return args -> {
      List<Student> students = List.of(
          student("2026-001", "Sebastián", "Llamuca", "75629082", "sebastian.llamuca@gmail.com",
              "987654321", "Av. Los Olivos 123, Lima", StudentStatus.ACTIVE),
          student("2026-002", "María", "Quispe", "71234567", "maria.quispe@gmail.com", "986543210",
              "Jr. Las Flores 456, Lima", StudentStatus.ACTIVE),
          student("2026-003", "Carlos", "Mendoza", "70654321", "carlos.mendoza@gmail.com",
              "985432109", "Av. Brasil 789, Lima", StudentStatus.ACTIVE),
          student("2026-004", "Lucía", "Torres", "72345678", "lucia.torres@gmail.com", "984321098",
              "Calle Los Pinos 234, Lima", StudentStatus.ACTIVE),
          student("2026-005", "Diego", "Ramírez", "73456789", "diego.ramirez@gmail.com",
              "983210987", "Av. Arequipa 1010, Lima", StudentStatus.ACTIVE),
          student("2026-006", "Ana", "Flores", "74567890", "ana.flores@gmail.com", "982109876",
              "Jr. Cusco 321, Lima", StudentStatus.ACTIVE),
          student("2026-007", "Jorge", "Castillo", "75678901", "jorge.castillo@gmail.com",
              "981098765", "Av. La Marina 555, Lima", StudentStatus.ACTIVE),
          student("2026-008", "Valeria", "Ríos", "76789012", "valeria.rios@gmail.com", "980987654",
              "Calle Real 678, Lima", StudentStatus.INACTIVE),
          student("2026-009", "Andrés", "Vega", "77890123", "andres.vega@gmail.com", "979876543",
              "Av. Universitaria 890, Lima", StudentStatus.ACTIVE),
          student("2026-010", "Camila", "Soto", "78901234", "camila.soto@gmail.com", "978765432",
              "Jr. Junín 147, Lima", StudentStatus.ACTIVE)
      );
      students.forEach(s -> {
        if (!repository.existsByCode(s.getCode())) {
          repository.save(s);
        }
      });
    };
  }
  
  // ---------- helpers ----------
  
  private AcademicProgram program(String code, String name, String description, BigDecimal price) {
    AcademicProgram p = new AcademicProgram();
    p.setCode(code);
    p.setName(name);
    p.setDescription(description);
    p.setActive(true);
    p.setPrice(price);
    return p;
  }
  
  private AcademicPeriod period(String name, LocalDate startDate, LocalDate endDate,
      boolean active) {
    AcademicPeriod p = new AcademicPeriod();
    p.setName(name);
    p.setStartDate(startDate);
    p.setEndDate(endDate);
    p.setActive(active);
    return p;
  }
  
  private Student student(String code, String firstName, String lastName, String documentNumber,
      String email, String phone, String address, StudentStatus status) {
    Student s = new Student();
    s.setCode(code);
    s.setFirstName(firstName);
    s.setLastName(lastName);
    s.setDocumentType(DocumentType.DNI);
    s.setDocumentNumber(documentNumber);
    s.setEmail(email);
    s.setPhone(phone);
    s.setAddress(address);
    s.setStatus(status);
    return s;
  }
  
}
