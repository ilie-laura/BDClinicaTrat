package com.clinica.web.controller;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.model.Tratament;
import com.clinica.web.service.TratamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TratamentController {

    private final TratamentService tratamentService;

    @Autowired
    public TratamentController(TratamentService tratamentService) {
        this.tratamentService = tratamentService;
    }

    // Lista tratamentelor
    @GetMapping("/tratamente")
    public String tratamente(Model model){
        List<TratamentDto> tratamente = tratamentService.findAllTrataments();
        model.addAttribute("tratamente", tratamente);
        return "tratamente";
    }

    // Formular adaugare
    @GetMapping("/addTratament")
    public String addTratament(Model model) {
        model.addAttribute("tratament", new TratamentDto());
        return "addTratament"; // fișier HTML pentru adaugare
    }

    // Salvare tratament
    @PostMapping("/addTratament")
    public String saveTratament(@ModelAttribute("tratament") TratamentDto dto) {
        tratamentService.saveTratament(dto);
        return "redirect:/tratamente";
    }

    // Căutare
    @GetMapping("/tratamente/search")
    public String searchTratamente(@RequestParam String field,
                                   @RequestParam String value,
                                   Model model) {
        System.out.println("Căutare tratament: field=" + field + "  value=" + value);
        List<TratamentDto> tratamente;
        if (value != null && !value.trim().isEmpty() && field != null) {

            tratamente = tratamentService.search(field, value);
        } else {

            tratamente = tratamentService.findAllTrataments();
        }

        model.addAttribute("tratamente", tratamente);
        return "tratamente";
    }


    @GetMapping("/tratamente/delete/{id}")
    public String delete(@PathVariable Long id) {
        tratamentService.deleteById(id);
        return "redirect:/tratamente";
    }

    @GetMapping("/tratamente/update/{id}")
    public String updateTratament(@PathVariable Long id, Model model) {
        Tratament tratament = tratamentService.findById(id);
        model.addAttribute("tratament", tratament);
        return "updateTratament";
    }


    @PostMapping("/tratamente/update/{id}")
    public String update(@ModelAttribute Tratament tratament) {
        tratamentService.update(tratament);
        return "redirect:/tratamente";
    }

}
