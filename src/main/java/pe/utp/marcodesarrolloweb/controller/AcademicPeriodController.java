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
  
  @ModelAttribute("activeMenu")
  public String activeMenu() {
    return "periods";
  }

  @GetMapping
  public String list(@RequestParam(defaultValue = "0") int page, Model model) {
    Page<AcademicPeriod> periodPage = academicPeriodService.findPage(page, 10);
    model.addAttribute("page", periodPage);
    model.addAttribute("academicPeriods", periodPage.getContent());
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

    if (academicPeriod.getStartDate() != null && academicPeriod.getEndDate() != null
        && academicPeriodService.hasOverlap(academicPeriod.getStartDate(), academicPeriod.getEndDate())) {
      result.rejectValue("startDate", "overlap",
          "Las fechas se cruzan con otro periodo académico existente");
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

    if (academicPeriod.getStartDate() != null && academicPeriod.getEndDate() != null
        && academicPeriodService.hasOverlap(academicPeriod.getStartDate(), academicPeriod.getEndDate(), id)) {
      result.rejectValue("startDate", "overlap",
          "Las fechas se cruzan con otro periodo académico existente");
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

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
      academicPeriodService.delete(id);
    } catch (IllegalStateException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/app/secure/academicPeriod";
  }
}
