package pe.utp.marcodesarrolloweb.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
  
  @ModelAttribute("activeMenu")
  public String activeMenu() {
    return "programs";
  }

  @GetMapping
  public String list(@RequestParam(defaultValue = "0") int page, Model model) {
    Page<AcademicProgram> programPage = academicProgramService.findPage(page, 10);
    model.addAttribute("page", programPage);
    model.addAttribute("academicPrograms", programPage.getContent());
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
  public String create(@Valid @ModelAttribute AcademicProgram academicProgram,
      BindingResult result) {
    
    if (academicProgram.getCode() != null
        && academicProgramService.isDuplicateCode(academicProgram.getCode())) {
      result.rejectValue("code", "duplicate", "Ya existe un programa con ese código");
    }
    
    if (result.hasErrors()) {
      return "academicProgram/form";
    }
    
    academicProgramService.save(academicProgram);
    return "redirect:/app/secure/academicProgram";
  }
  
  
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    model.addAttribute("academicProgram", academicProgramService.findById(id));
    return "academicProgram/form";
  }
  
  @PostMapping("/{id}")
  public String update(@PathVariable Long id,
      @Valid @ModelAttribute AcademicProgram academicProgram,
      BindingResult result) {
    
    if (academicProgram.getCode() != null
        && academicProgramService.isDuplicateCode(academicProgram.getCode(), id)) {
      result.rejectValue("code", "duplicate", "Ya existe otro programa con ese código");
    }
    
    if (result.hasErrors()) {
      academicProgram.setId(id);
      return "academicProgram/form";
    }
    
    AcademicProgram existing = academicProgramService.findById(id);
    existing.setCode(academicProgram.getCode());
    existing.setName(academicProgram.getName());
    existing.setDescription(academicProgram.getDescription());
    existing.setActive(academicProgram.getActive());
    academicProgramService.save(existing);
    
    return "redirect:/app/secure/academicProgram/" + id;
  }

  @PostMapping("/{id}/toggle-active")
  public String toggleActive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    academicProgramService.toggleActive(id);
    return "redirect:/app/secure/academicProgram";
  }
}
