package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.utp.marcodesarrolloweb.model.StudentLocalModel;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;
import pe.utp.marcodesarrolloweb.model.enums.StudentStatus;
import pe.utp.marcodesarrolloweb.service.StudentService;

@Controller
@RequestMapping("/student")
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
    StudentLocalModel studentLocalModel = studentService.findById(id);
    model.addAttribute("student", studentLocalModel);
    return "student/detail";
  }
  
  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("student", new StudentLocalModel());
    model.addAttribute("documentTypes", DocumentType.values());
    model.addAttribute("studentStatuses", StudentStatus.values());
    return "student/form";
  }
  
  @PostMapping
  public String create(@ModelAttribute StudentLocalModel studentLocalModel) {
    studentService.save(studentLocalModel);
    return "redirect:/student";
  }
  
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    StudentLocalModel studentLocalModel = studentService.findById(id);
    model.addAttribute("student", studentLocalModel);
    model.addAttribute("documentTypes", DocumentType.values());
    model.addAttribute("studentStatuses", StudentStatus.values());
    return "student/form";
  }
  
  @PostMapping("/{id}")
  public String update(@PathVariable Long id, @ModelAttribute StudentLocalModel studentLocalModel) {
    studentLocalModel.setId(id);
    studentService.update(studentLocalModel);
    return "redirect:/student/" + id;
  }
  
}

