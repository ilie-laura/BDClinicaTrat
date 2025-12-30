package com.clinica.web.controller;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.MedicRepository;
import com.clinica.web.service.MedicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MedicController {
    private final MedicService medicService;
    public MedicController(MedicService medicService) {
        this.medicService = medicService;
    }
    @GetMapping("/medics")

    public String medics(Model model,
                         @RequestParam(required = false) String field,
                         @RequestParam(required = false) String value,
                         @RequestParam(required = false) Boolean dir,
                         @RequestParam(required = false) String order ) {

        boolean currentDir = (dir == null) ? true : dir;
        if (order != null) {
            currentDir = !currentDir;
        }

        boolean nextDir = !currentDir;
        model.addAttribute("dir", currentDir);
        model.addAttribute("nextDir", nextDir);
        List<Medic> medici ;
        System.out.println("Search parameters: field=" + field + ", value=" + value);
        model.addAttribute("field", field);
        if (value != null && !value.trim().isEmpty() && field != null) {
            medici = medicService.search(field, value,currentDir);
        } else {
            medici = medicService.findAll(currentDir, field);
        }

        Map<Integer, Integer> nrProgramari =
                medicService.findNrProgramariPerMedic();
        model.addAttribute("nrProgramari", nrProgramari);
        Map<Integer, List<String>> pacientiPerMedic =
                medicService.findPacientiPerMedic();

        model.addAttribute("pacientiPerMedic", pacientiPerMedic);

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
    public String updateMedic(@PathVariable Long id, Model model) {
        Medic medic = medicService.findById(id);
        model.addAttribute("medic", medic);
        return "updateMedic";
    }


    @PostMapping("/medics/update/{id}")
    public String update(@ModelAttribute Medic pacient) {
        medicService.update(pacient);
        return "redirect:/medics";
    }
}
