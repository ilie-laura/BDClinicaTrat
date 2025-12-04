package com.clinica.web.controller;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.dto.TratamentDto;
import com.clinica.web.service.PrescriereTratamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        System.out.println("Prescrieri size: " + prescrieri.size());
        prescrieri.forEach(p -> System.out.println(p));

        model.addAttribute("prescrieri",prescrieri);
        return "prescrieri";
    }

    // Formular adaugare
    @GetMapping("/addPrescriere")
    public String addPrescriere(Model model) {
        model.addAttribute("prescrieri", new PrescriereTratamentDto());
        return "addPrescriere";
    }
    // Salvare
    @PostMapping("/addPrescriere")
    public String savePrescriere(@ModelAttribute("prescrieri") PrescriereTratamentDto dto, Model model) {
        try {
            prescriereTratamentService.savePrescriere(dto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "addPrescriere"; // rămâne pe formular
        }
        return "redirect:/prescrieri";
    }

    // Căutare
    @GetMapping("/prescrieri/search")
    public String searchPrescriere(@RequestParam String field,
                                   @RequestParam String value,
                                   Model model) {

        System.out.println("Căutare prescriere: field=" + field + "  value=" + value);

        List<PrescriereTratamentDto> tratamente;

        if (value != null && !value.trim().isEmpty() && field != null) {
            // Folosește metoda search din service
            tratamente = prescriereTratamentService.search(field, value);
        } else {
            // Dacă nu există valoare, afișează toate tratamentele
            tratamente = prescriereTratamentService.findAllPrescriereTrataments();
        }

        model.addAttribute("prescrieri", tratamente);
        return "prescrieri";
    }

}
