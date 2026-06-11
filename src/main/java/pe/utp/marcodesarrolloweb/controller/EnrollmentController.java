package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    model.addAttribute("students", studentService.findAll());
    model.addAttribute("programs", academicProgramService.findAll());
    model.addAttribute("periods", academicPeriodService.findAll());
    return "enrollment/form";
  }
  
  @PostMapping
  public String create(@RequestParam Long studentId,
      @RequestParam Long academicProgramId,
      @RequestParam Long academicPeriodId,
      @RequestParam(required = false) String observation,
      RedirectAttributes redirectAttributes) {
    try {
      enrollmentService.create(studentId, academicProgramId, academicPeriodId, observation);
    } catch (IllegalStateException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/app/secure/enrollment/new";
    }
    return "redirect:/app/secure/enrollment";
  }
}
