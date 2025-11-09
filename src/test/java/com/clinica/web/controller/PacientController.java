import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PacientController {

    @GetMapping("/listPacients")
    public String listPacients(Model model) {
        // adaugă date în model dacă vrei
        return "listPacients";  // exact numele fișierului din templates
    }
    @GetMapping("/pacients")
    public String pacientsPage() {
        return "pacients"; // Thymeleaf template name (pacients.html)
    }
}
