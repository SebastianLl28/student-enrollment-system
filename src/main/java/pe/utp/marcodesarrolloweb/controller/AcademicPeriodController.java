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
import pe.utp.marcodesarrolloweb.model.AcademicPeriod;
import pe.utp.marcodesarrolloweb.service.AcademicPeriodService;

/**
 * @author Alonso
 */
@Controller
@RequestMapping("/app/secure/academicPeriod")
public class AcademicPeriodController {
  
  private final AcademicPeriodService academicPeriodService;
  
  public AcademicPeriodController(AcademicPeriodService academicPeriodService) {
    this.academicPeriodService = academicPeriodService;
  }
  
  @GetMapping
  public String list(Model model) {
    model.addAttribute("academicPeriods", academicPeriodService.findAll());
    return "academicPeriod/list";
  }
  
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("academicPeriod", academicPeriodService.findById(id));
    return "academicPeriod/detail";
  }
  
  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("academicPeriod", new AcademicPeriod());
    return "academicPeriod/form";
  }
  
  @PostMapping
  public String create(@Valid @ModelAttribute AcademicPeriod academicPeriod,
      BindingResult result) {
    
    if (academicPeriod.getName() != null
        && academicPeriodService.isDuplicateName(academicPeriod.getName())) {
      result.rejectValue("name", "duplicate", "Ya existe un periodo con ese nombre");
    }
    
    if (result.hasErrors()) {
      return "academicPeriod/form";
    }
    
    academicPeriodService.save(academicPeriod);
    return "redirect:/app/secure/academicPeriod";
  }
  
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    model.addAttribute("academicPeriod", academicPeriodService.findById(id));
    return "academicPeriod/form";
  }
  
  @PostMapping("/{id}")
  public String update(@PathVariable Long id,
      @Valid @ModelAttribute AcademicPeriod academicPeriod,
      BindingResult result) {
    
    if (academicPeriod.getName() != null
        && academicPeriodService.isDuplicateName(academicPeriod.getName(), id)) {
      result.rejectValue("name", "duplicate", "Ya existe otro periodo con ese nombre");
    }
    
    if (result.hasErrors()) {
      academicPeriod.setId(id);
      return "academicPeriod/form";
    }
    
    AcademicPeriod existing = academicPeriodService.findById(id);
    existing.setName(academicPeriod.getName());
    existing.setStartDate(academicPeriod.getStartDate());
    existing.setEndDate(academicPeriod.getEndDate());
    existing.setActive(academicPeriod.getActive());
    academicPeriodService.save(existing);
    
    return "redirect:/app/secure/academicPeriod/" + id;
  }
}
