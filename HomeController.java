package pe.utp.marcodesarrolloweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio() {
        return "index";      // busca templates/index.html
    }

    @GetMapping("/pagos")
    public String pagos() {
        return "pagos";      // busca templates/pagos.html
    }

    @GetMapping("/programas")
    public String programas() {
        return "programas";  // busca templates/programas.html
    }

    // si quieres, puedes agregar rutas para cada programa:
    @GetMapping("/programas/administracion")
    public String programaAdministracion() {
        return "programa-administracion";
    }

    @GetMapping("/programas/comunicacion")
    public String programaComunicacion() {
        return "programa-comunicacion";
    }

    @GetMapping("/programas/datos")
    public String programaDatos() {
        return "programa-datos";
    }

    @GetMapping("/programas/derecho")
    public String programaDerecho() {
        return "programa-derecho";
    }

    @GetMapping("/programas/psicologia")
    public String programaPsicologia() {
        return "programa-psicologia";
    }

    @GetMapping("/programas/software")
    public String programaSoftware() {
        return "programa-software";
    }
}