package com.clinica.web.controller;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import com.clinica.web.service.MedicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class MedicController {
    private final MedicService medicService;
    public MedicController(MedicService medicService) {
        this.medicService = medicService;
    }
    @GetMapping("/medics")

    public String medics(Model model,@RequestParam(required = false) String field,
                         @RequestParam(required = false) String value) {
        List<Medic> medici ;

        System.out.println("Search parameters: field=" + field + ", value=" + value);

        if (value != null && !value.trim().isEmpty() && field != null) {
            medici = medicService.search(field, value);
        } else {
            medici = medicService.findAll();
        }

        model.addAttribute("medici", medici);
        return "medics";
    }

    @GetMapping("/addMedic")
    public String showAddForm(Model model) {
        model.addAttribute("medic", new Medic());
        return "addMedic";
    }
    @PostMapping("/addMedic")
    public String addPacient(@ModelAttribute Medic medic) {
        medicService.save(medic);
        return "redirect:/medics";
    }



    @GetMapping("/medics/delete/{id}")
    public String delete(@PathVariable Long id) {
        medicService.deleteById(id);
        return "redirect:/medics";
    }

    @GetMapping("/medics/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Medic medic = medicService.findById(id);
        model.addAttribute("medic", medic);
        return "update";
    }


    @PostMapping("/medics/update/{id}")
    public String update(@ModelAttribute Medic pacient) {
        medicService.update(pacient);
        return "redirect:/medics";
    }
}
