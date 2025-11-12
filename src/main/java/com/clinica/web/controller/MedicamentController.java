package com.clinica.web.controller;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.service.MedicamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class MedicamentController {
    private final MedicamentService medicamentService;
    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }
    @GetMapping("/medicaments")
    public String medicaments(Model model){
        List<MedicamentDto> medicamente=medicamentService.findAllMedicaments();
        model.addAttribute("medicaments",medicamente);
        return "medicaments";

    }
}
