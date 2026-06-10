package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.utp.marcodesarrolloweb.model.AcademicProgram;
import pe.utp.marcodesarrolloweb.service.AcademicProgramService;

/**
 * @author Alonso
 */

@Controller
@RequestMapping("/app/secure/academicProgram")
public class AcademicProgramController {
  
  private final AcademicProgramService academicProgramService;
  
  public AcademicProgramController(AcademicProgramService academicProgramService) {
    this.academicProgramService = academicProgramService;
  }
  
  @GetMapping
  public String list(Model model) {
    model.addAttribute("academicPrograms", academicProgramService.findAll());
    return "academicProgram/list";
  }
  
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("academicProgram", academicProgramService.findById(id));
    return "academicProgram/detail";
  }
  
  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("academicProgram", new AcademicProgram());
    return "academicProgram/form";
  }
  
  @PostMapping
  public String create(@ModelAttribute AcademicProgram academicProgram) {
    academicProgramService.save(academicProgram);
    return "redirect:/app/secure/academicProgram";
  }
  
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    model.addAttribute("academicProgram", academicProgramService.findById(id));
    return "academicProgram/form";
  }
  
  @PostMapping("/{id}")
  public String update(@PathVariable Long id, @ModelAttribute AcademicProgram formData) {
    AcademicProgram academicProgram = academicProgramService.findById(id);
    academicProgram.setCode(formData.getCode());
    academicProgram.setName(formData.getName());
    academicProgram.setDescription(formData.getDescription());
    academicProgram.setActive(formData.getActive());
    academicProgramService.save(academicProgram);
    return "redirect:/app/secure/academicProgram/" + id;
  }
}
