package com.clinica.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }
    @PostMapping("/submit-contact")
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            Model model) {

        model.addAttribute("success", "Mesajul a fost trimis cu succes!");

        return "contact"; // pagina ta contact.html
    }
}
