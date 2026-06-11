package pe.utp.marcodesarrolloweb.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
  public String create(@Valid @ModelAttribute Student student,
      BindingResult result, Model model) {
    
    if (student.getDocumentType() != null && student.getDocumentNumber() != null
        && studentService.isDuplicateDocument(student.getDocumentType(), student.getDocumentNumber())) {
      result.rejectValue("documentNumber", "duplicate",
          "Ya existe un estudiante con ese tipo y número de documento");
    }
    
    if (result.hasErrors()) {
      model.addAttribute("documentTypes", DocumentType.values());
      model.addAttribute("studentStatuses", StudentStatus.values());
      return "student/form";
    }
    
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
  public String update(@PathVariable Long id,
      @Valid @ModelAttribute Student student,
      BindingResult result, Model model) {
    
    if (student.getDocumentType() != null && student.getDocumentNumber() != null
        && studentService.isDuplicateDocument(student.getDocumentType(), student.getDocumentNumber(), id)) {
      result.rejectValue("documentNumber", "duplicate",
          "Ya existe otro estudiante con ese tipo y número de documento");
    }
    
    if (result.hasErrors()) {
      student.setId(id);
      model.addAttribute("documentTypes", DocumentType.values());
      model.addAttribute("studentStatuses", StudentStatus.values());
      return "student/form";
    }
    
    Student existing = studentService.findById(id);
    existing.setCode(student.getCode());
    existing.setFirstName(student.getFirstName());
    existing.setLastName(student.getLastName());
    existing.setDocumentType(student.getDocumentType());
    existing.setDocumentNumber(student.getDocumentNumber());
    existing.setEmail(student.getEmail());
    existing.setPhone(student.getPhone());
    existing.setAddress(student.getAddress());
    existing.setStatus(student.getStatus());
    studentService.save(existing);
    
    return "redirect:/app/secure/student/" + id;
  }
}
