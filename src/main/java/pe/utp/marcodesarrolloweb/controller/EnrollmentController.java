package pe.utp.marcodesarrolloweb.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.utp.marcodesarrolloweb.service.AcademicPeriodService;
import pe.utp.marcodesarrolloweb.service.AcademicProgramService;
import pe.utp.marcodesarrolloweb.service.EnrollmentService;
import pe.utp.marcodesarrolloweb.service.StudentService;

/**
 * @author Alonso
 */


@Controller
@RequestMapping("/app/secure/enrollment")
public class EnrollmentController {
  
  private final EnrollmentService enrollmentService;
  private final StudentService studentService;
  private final AcademicProgramService academicProgramService;
  private final AcademicPeriodService academicPeriodService;
  
  public EnrollmentController(EnrollmentService enrollmentService,
      StudentService studentService,
      AcademicProgramService academicProgramService,
      AcademicPeriodService academicPeriodService) {
    this.enrollmentService = enrollmentService;
    this.studentService = studentService;
    this.academicProgramService = academicProgramService;
    this.academicPeriodService = academicPeriodService;
  }
  
  @ModelAttribute("activeMenu")
  public String activeMenu() {
    return "enrollments";
  }
  
  @GetMapping
  public String list(Model model) {
    model.addAttribute("enrollments", enrollmentService.findAll());
    return "enrollment/list";
  }
  
  @GetMapping("/new")
  public String createForm(Model model) {
    addSelectData(model);
    model.addAttribute("errors", new HashMap<String, String>());
    return "enrollment/form";
  }
  
  @PostMapping
  public String create(@RequestParam(required = false) Long studentId,
      @RequestParam(required = false) Long academicProgramId,
      @RequestParam(required = false) Long academicPeriodId,
      @RequestParam(required = false) String observation,
      Model model) {
    
    Map<String, String> errors = new HashMap<>();
    if (studentId == null)         errors.put("student", "Seleccione un estudiante");
    if (academicProgramId == null) errors.put("program", "Seleccione un programa académico");
    if (academicPeriodId == null)  errors.put("period",  "Seleccione un periodo académico");
    
    if (errors.isEmpty()) {
      try {
        enrollmentService.create(studentId, academicProgramId, academicPeriodId, observation);
        return "redirect:/app/secure/enrollment";
      } catch (IllegalStateException e) {
        errors.put("global", e.getMessage());
      }
    }
    
    model.addAttribute("errors", errors);
    model.addAttribute("selectedStudentId", studentId);
    model.addAttribute("selectedProgramId", academicProgramId);
    model.addAttribute("selectedPeriodId", academicPeriodId);
    model.addAttribute("observation", observation);
    addSelectData(model);
    return "enrollment/form";
  }
  
  private void addSelectData(Model model) {
    model.addAttribute("students", studentService.findAll());
    model.addAttribute("programs", academicProgramService.findAll());
    model.addAttribute("periods", academicPeriodService.findAll());
  }
}
