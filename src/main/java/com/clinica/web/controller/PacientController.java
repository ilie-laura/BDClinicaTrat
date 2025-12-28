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
            @RequestParam(required = false) Boolean dir,
            @RequestParam(required = false) String order,

            Model model
    ) {
        boolean currentDir = (dir == null) ? true : dir;
        if (order != null) {
            currentDir = !currentDir;
        }

        boolean nextDir = !currentDir;
        model.addAttribute("dir", currentDir);
        model.addAttribute("nextDir", nextDir);

        List<Pacient> pacienti;
        System.out.println("field=" + field + ", value=" + value);
        model.addAttribute("field", field);

        if (value != null && !value.isEmpty()) {
            pacienti = pacientService.search(field, value, currentDir);
        } else {
            pacienti = pacientService.findAll(currentDir,field);
        }

        model.addAttribute("pacienti", pacienti);

        return "listPacients";
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
