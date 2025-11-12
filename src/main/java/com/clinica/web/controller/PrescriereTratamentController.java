package com.clinica.web.controller;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.service.PrescriereTratamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PrescriereTratamentController {
    private final PrescriereTratamentService prescriereTratamentService;
    @Autowired
    public PrescriereTratamentController (PrescriereTratamentService prescriereTratamentService) {
        this.prescriereTratamentService = prescriereTratamentService;
    }
    @GetMapping("/prescrieri")
    public String prescrieri(Model model){
        List<PrescriereTratamentDto> prescrieri=prescriereTratamentService.findAllPrescriereTrataments();
        model.addAttribute("prescrieri",prescrieri);
        return "prescrieri";
    }

}
