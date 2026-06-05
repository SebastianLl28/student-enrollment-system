package pe.utp.marcodesarrolloweb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import pe.utp.marcodesarrolloweb.model.CustomUserDetails;
import pe.utp.marcodesarrolloweb.model.enums.DocumentType;

/**
 * @author Alonso
 */
@Controller
public class AuthController {
  
  @GetMapping("/login")
  public String login() {
    System.out.println("GET /login");
    return "login";
  }
  
  @GetMapping("/app/secure/home")
  public String home(@AuthenticationPrincipal CustomUserDetails principal, Model model) {
    
    model.addAttribute("user", principal.getUsername());
    return "home";
  }
  
  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("documentTypes", DocumentType.values());
    return "register";
  }

}
