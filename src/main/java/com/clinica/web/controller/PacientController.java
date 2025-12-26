package com.clinica.web.controller;

import com.clinica.web.model.Pacient;
import com.clinica.web.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class PacientController {

    private final PacientService pacientService;

    @Autowired
    public PacientController(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    @GetMapping("/listPacients")
    public String listPacients(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            Model model
    ) {

        List<Pacient> pacienti;
        System.out.println("field=" + field + ", value=" + value);

        if (value != null && !value.isEmpty()) {
            pacienti = pacientService.search(field, value);
        } else {
            pacienti = pacientService.findAll();
        }

        model.addAttribute("pacienti", pacienti);

        return "listPacients"; // numele paginii HTML
    }
    @GetMapping("/addPacient")
    public String showAddForm(Model model) {
        model.addAttribute("pacient", new Pacient()); // obiect gol pentru form
        return "addPacient";
    }
    @PostMapping("/addPacient")
    public String addPacient(@ModelAttribute Pacient pacient) {
        pacientService.save(pacient);
        return "redirect:/listPacients";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        pacientService.deleteById(id);
        return "redirect:/listPacients";
    }

    @GetMapping("/listPacients/update/{id}")
    public String updatePacient(@PathVariable Long id, Model model) {
        Pacient pacient = pacientService.findById(id);
        model.addAttribute("pacient", pacient);
        return "updatePacient";
    }


    @PostMapping("/listPacients/update/{id}")
    public String update(@ModelAttribute Pacient pacient) {
        pacientService.update(pacient);
        return "redirect:/listPacients";
    }
}
