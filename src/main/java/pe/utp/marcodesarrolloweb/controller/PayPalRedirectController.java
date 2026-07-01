package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Alonso
 */
@Controller
@RequestMapping("/payments/paypal")
public class PayPalRedirectController {
  
  @GetMapping("/return")
  public String paymentReturn() {
    return "payments/paypal-return";
  }
  
  @GetMapping("/cancel")
  public String paymentCancel() {
    return "payments/paypal-cancel";
  }
}
