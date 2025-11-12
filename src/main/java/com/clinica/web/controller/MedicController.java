package com.clinica.web.controller;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.dto.PacientDto;
import com.clinica.web.service.MedicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class MedicController {
    private final MedicService medicService;
    public MedicController(MedicService medicService) {
        this.medicService = medicService;
    }
    @GetMapping("/medics")

    public String medics(Model model) {
        List<MedicDto> medici = medicService.findAllMedics(); // sau orice metodă ai
        model.addAttribute("medici", medici);
        return "medics";
    }
}
