package com.clinica.web.controller;

import com.clinica.web.repository.RapoarteRepository;
import com.clinica.web.service.MedicService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class WebController {
    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (principal != null) {
            model.addAttribute("currentUsername", principal.getName());
        }

        model.addAttribute("mediciActivi",
                RapoarteRepository.mediciActiviAzi());
        
        // Verifică dacă obiectul de autentificare este valid și nu este utilizatorul anonim
        if (auth != null && auth.isAuthenticated() &&
                auth.getPrincipal() instanceof UserDetails) {

            String username = auth.getName();

            // Debugging: Arată numele în consolă
            System.out.println("--- UTILIZATOR LOGAT: " + username + " ---");

            // Setează variabila NUMAI dacă utilizatorul este logat
            model.addAttribute("currentUsername", username);

        } else {
            System.out.println("--- UTILIZATOR ANONIM DETECTAT ---");

        }


        return "index";
    }



}