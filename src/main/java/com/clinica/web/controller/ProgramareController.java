package com.clinica.web.controller;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.service.PacientService;
import com.clinica.web.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProgramareController {
    private final ProgramareService programareService;

    @Autowired
    public ProgramareController(ProgramareService programareService) {
        this.programareService = programareService;
    }
    @GetMapping("/programari")

    public String programari(Model model) {
        List<ProgramareDto> programari = programareService.findAllProgramari(); // sau orice metodă ai
        model.addAttribute("programari", programari);
        return "programari";
    }
//    @GetMapping("/pacients")
//    public String pacientsPage() {
//        return "pacients"; // Thymeleaf template name (pacients.html)
//    }
}
