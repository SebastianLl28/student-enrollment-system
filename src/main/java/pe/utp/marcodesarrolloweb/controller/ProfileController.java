package pe.utp.marcodesarrolloweb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.utp.marcodesarrolloweb.model.CustomUserDetails;

/**
 * @author Alonso
 */
@Controller
@RequestMapping("/app/secure/profile")
public class ProfileController {

  @GetMapping
  public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
    model.addAttribute("profileUser", userDetails.getUser());
    model.addAttribute("activeMenu", "profile");
    return "profile/profile";
  }
}
