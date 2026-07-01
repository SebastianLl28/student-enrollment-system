package pe.utp.marcodesarrolloweb.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import pe.utp.marcodesarrolloweb.model.CustomUserDetails;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.EnrollmentStatus;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;
import pe.utp.marcodesarrolloweb.repository.AcademicPeriodRepository;
import pe.utp.marcodesarrolloweb.repository.AcademicProgramRepository;
import pe.utp.marcodesarrolloweb.repository.EnrollmentRepository;
import pe.utp.marcodesarrolloweb.repository.StudentRepository;

/**
 * @author Alonso
 */
@Controller
public class AuthController {

  private final StudentRepository studentRepository;
  private final AcademicProgramRepository programRepository;
  private final AcademicPeriodRepository periodRepository;
  private final EnrollmentRepository enrollmentRepository;

  public AuthController(StudentRepository studentRepository,
      AcademicProgramRepository programRepository,
      AcademicPeriodRepository periodRepository,
      EnrollmentRepository enrollmentRepository) {
    this.studentRepository = studentRepository;
    this.programRepository = programRepository;
    this.periodRepository = periodRepository;
    this.enrollmentRepository = enrollmentRepository;
  }

  @ModelAttribute("activeMenu")
  public String activeMenu() {
    return "home";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/app/secure/home")
  public String home(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
    model.addAttribute("user", principal.getUsername());
    model.addAttribute("today", LocalDate.now()
        .format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "PE"))));

    model.addAttribute("totalStudents", studentRepository.countByStatus(StudentStatus.ACTIVE));
    model.addAttribute("totalPrograms", programRepository.countByActiveTrue());
    model.addAttribute("enrolledCount", enrollmentRepository.countByStatus(EnrollmentStatus.PAID));
    model.addAttribute("pendingCount", enrollmentRepository.countByStatus(EnrollmentStatus.PENDING_PAYMENT));
    model.addAttribute("cancelledCount", enrollmentRepository.countByStatus(EnrollmentStatus.CANCELLED));

    model.addAttribute("currentPeriod",
        periodRepository.findCurrentPeriod(LocalDate.now()).orElse(null));

    List<Object[]> byProgram = enrollmentRepository.countEnrollmentsByProgram();
    model.addAttribute("programLabels",
        byProgram.stream().map(r -> (String) r[0]).collect(Collectors.toList()));
    model.addAttribute("programData",
        byProgram.stream().map(r -> (Long) r[1]).collect(Collectors.toList()));

    return "home";
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("documentTypes", DocumentType.values());
    return "register";
  }
}
