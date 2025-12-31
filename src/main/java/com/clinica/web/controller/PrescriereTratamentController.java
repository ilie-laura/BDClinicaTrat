package com.clinica.web.controller;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Tratament;
import com.clinica.web.service.PrescriereTratamentService;
import com.clinica.web.service.TratamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PrescriereTratamentController {
    private final PrescriereTratamentService prescriereTratamentService;
    private final TratamentService tratamentService;

    @Autowired
    public PrescriereTratamentController (PrescriereTratamentService prescriereTratamentService, TratamentService tratamentService) {
        this.prescriereTratamentService = prescriereTratamentService;
        this.tratamentService = tratamentService;
    }
    @GetMapping("/prescrieri")
    public String prescrieri(Model model){

        List<PrescriereTratamentDto> prescrieri =
                prescriereTratamentService.findAllPrescriereTrataments();

        prescrieri.forEach(p -> {
            p.setTratamentNume(
                    tratamentService.getNumeTratamentById(p.getTratamentID())
            );
            p.setProgramareInfo(
                    prescriereTratamentService.getProgramareInfo(p.getProgramareID())
            );
        });
        enrichPrescrieri(prescrieri);
        model.addAttribute("prescrieri", prescrieri);
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
        enrichPrescrieri(tratamente);
        model.addAttribute("prescrieri", tratamente);
        return "prescrieri";
    }
    private void enrichPrescrieri(List<PrescriereTratamentDto> prescrieri) {

        Map<Long, String> tratamentCache = new HashMap<>();
        Map<Long, String> programareCache = new HashMap<>();

        for (PrescriereTratamentDto p : prescrieri) {

            tratamentCache.computeIfAbsent(
                    p.getTratamentID(),
                    id -> tratamentService.getNumeTratamentById(id)
            );

            programareCache.computeIfAbsent(
                    p.getProgramareID(),
                    id -> prescriereTratamentService.getProgramareInfo(id)
            );

            p.setTratamentNume(tratamentCache.get(p.getTratamentID()));
            p.setProgramareInfo(programareCache.get(p.getProgramareID()));
        }
    }

}
