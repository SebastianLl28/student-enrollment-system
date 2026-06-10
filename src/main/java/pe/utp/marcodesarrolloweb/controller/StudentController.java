package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.utp.marcodesarrolloweb.model.Student;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;
import pe.utp.marcodesarrolloweb.service.StudentService;

@Controller
@RequestMapping("/app/secure/student")
public class StudentController {
  
  private final StudentService studentService;
  
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }
  
  @GetMapping
  public String list(Model model) {
    model.addAttribute("students", studentService.findAll());
    return "student/list";
  }
  
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.findById(id));
    return "student/detail";
  }
  
  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("documentTypes", DocumentType.values());
    model.addAttribute("studentStatuses", StudentStatus.values());
    return "student/form";
  }
  
  @PostMapping
  public String create(@ModelAttribute Student student) {
    studentService.save(student);
    return "redirect:/app/secure/student";
  }
  
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    model.addAttribute("student", studentService.findById(id));
    model.addAttribute("documentTypes", DocumentType.values());
    model.addAttribute("studentStatuses", StudentStatus.values());
    return "student/form";
  }
  
  @PostMapping("/{id}")
  public String update(@PathVariable Long id, @ModelAttribute Student formData) {
    Student student = studentService.findById(id);
    student.setCode(formData.getCode());
    student.setFirstName(formData.getFirstName());
    student.setLastName(formData.getLastName());
    student.setDocumentType(formData.getDocumentType());
    student.setDocumentNumber(formData.getDocumentNumber());
    student.setEmail(formData.getEmail());
    student.setPhone(formData.getPhone());
    student.setAddress(formData.getAddress());
    student.setStatus(formData.getStatus());
    studentService.save(student);
    return "redirect:/app/secure/student/" + id;
  }
}
