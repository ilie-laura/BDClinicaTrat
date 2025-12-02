package com.clinica.web.controller;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.service.PacientService;
import com.clinica.web.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProgramareController {
    private final ProgramareService programareService;

    @Autowired
    public ProgramareController(ProgramareService programareService) {
        this.programareService = programareService;
    }
    @GetMapping("/programari")

    public String programari(Model model) {
        List<ProgramareDto> programari = programareService.findAllProgramari(); // sau orice metodă ai
        model.addAttribute("programari", programari);
        return "programari";
    }
    @GetMapping("/programari/search")
    public String searchProgramari(String field, String value, Model model) {

        System.out.println("Cautare: field=" + field + "  value=" + value);

        List<ProgramareDto> programari;

        if (value != null && !value.trim().isEmpty() && field != null) {
            programari = programareService.search(field, value);
        } else {
            model.addAttribute("programari", programareService.findAllProgramari());
            return "programari";
        }

        model.addAttribute("programari", programari);
        return "programari";
    }
    @GetMapping("/addProgramare")
    public String showAddProgramareForm(Model model) {
        model.addAttribute("programare", new ProgramareDto());
        return "addProgramare";
    }
    @PostMapping("/addProgramare")
    public String saveProgramare(@ModelAttribute("programare") ProgramareDto programareDto) {

        programareService.save(programareDto);

        return "redirect:/programari";
    }

}
