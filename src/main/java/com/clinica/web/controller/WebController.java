package com.clinica.web.controller;

import com.clinica.web.model.Medic;
import com.clinica.web.repository.PacientJdbcRepository;
import com.clinica.web.repository.RapoarteRepository;
import com.clinica.web.service.MedicService;
import com.clinica.web.service.ProgramareService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class WebController {
    private final MedicService medicService;
    private final ProgramareService programareService;
    private final PacientJdbcRepository pacientJdbcRepository;


    public WebController(MedicService medicService,
                         ProgramareService programareService,
                         RapoarteRepository rapoarteRepository, PacientJdbcRepository pacientJdbcRepository) {
        this.medicService = medicService;
        this.programareService = programareService;
        ;
        this.pacientJdbcRepository = pacientJdbcRepository;
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (principal != null) {
            model.addAttribute("currentUsername", principal.getName());
        }

        model.addAttribute("mediciActivi",
                RapoarteRepository.mediciActiviAzi());
        
        // Verifică dacă obiectul de autentificare este valid
        if (auth != null && auth.isAuthenticated() &&
                auth.getPrincipal() instanceof UserDetails) {

            String username = auth.getName();

            System.out.println("--- UTILIZATOR LOGAT: " + username + " ---");

            model.addAttribute("currentUsername", username);

        } else {
            System.out.println("--- UTILIZATOR ANONIM DETECTAT ---");

        }

        model.addAttribute("medici", medicService.findAll(null, null));
        return "index";
    }

    @PostMapping("/programare")
    public String submitProgramare(@RequestParam String cnp,
                                   @RequestParam int medicID,
                                   @RequestParam String motiv,
                                   @RequestParam LocalDate appointment_date,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        boolean exista = pacientJdbcRepository.existsByCnp(cnp);

        try {
            if (!exista) {
                redirectAttributes.addFlashAttribute("error", "CNP-ul introdus nu există!");

                model.addAttribute("medici", medicService.findAll(null, null));
                model.addAttribute("mediciActivi", RapoarteRepository.mediciActiviAzi());

                if (principal != null) {
                    model.addAttribute("currentUsername", principal.getName());
                }

              return "redirect:/index";
        }

            programareService.creeazaProgramare(
                    cnp, medicID, appointment_date, motiv
            );
            return "redirect:/index";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("medici", medicService.findAll(null, null));
            model.addAttribute("mediciActivi", RapoarteRepository.mediciActiviAzi());
            return "redirect:/index";
        }

    }


}