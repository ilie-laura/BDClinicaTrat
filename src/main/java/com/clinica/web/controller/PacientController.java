package com.clinica.web.controller;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PacientController {
    private final PacientService pacientService;

    @Autowired
    public PacientController(PacientService pacientService) {
        this.pacientService = pacientService;
    }
    @GetMapping("/listPacients")

    public String listPacients(Model model) {
        List<PacientDto> pacienti = pacientService.findAllPacients();
        model.addAttribute("pacienti", pacienti);
        return "listPacients";
    }
//    @GetMapping("/pacients")
//    public String pacientsPage() {
//        return "pacients"; // Thymeleaf template name (pacients.html)
//    }
}
