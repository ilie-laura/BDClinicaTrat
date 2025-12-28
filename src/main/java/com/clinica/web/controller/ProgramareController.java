package com.clinica.web.controller;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Programare;
import com.clinica.web.model.Tratament;
import com.clinica.web.service.PacientService;
import com.clinica.web.service.ProgramareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProgramareController {
    private final ProgramareService programareService;

    @Autowired
    public ProgramareController(ProgramareService programareService) {
        this.programareService = programareService;
    }
    @GetMapping("/programari")

    public String programari(@RequestParam(required = false) String field,
                             @RequestParam(required = false) String value,
                             @RequestParam(required = false) Boolean dir,
                             @RequestParam(required = false) String order,
                             Model model) {
        boolean currentDir = (dir == null) ? true : dir;
        if (order != null) {
            currentDir = !currentDir;
        }

        boolean nextDir = !currentDir;
        model.addAttribute("dir", currentDir);
        model.addAttribute("nextDir", nextDir);
        model.addAttribute("field", field);
        List<ProgramareDto> programari;
        if (field != null && value != null && !value.isEmpty()) {
            // căutare
            List<ProgramareDto> lista = programareService.search(field, value,currentDir);
            programari = lista.stream()
                    .map(m -> ProgramareDto.builder()
                            .programareId(m.getProgramareId())
                            .pacientId(m.getPacientId())
                            .medicId(m.getMedicId())
                            .dataProgramare(m.getDataProgramare())
                            .durataProgramare(m.getDurataProgramare())
                            .build()
                    ).toList();

        } else {

            programari = programareService.findAll(currentDir,field);
        }
        model.addAttribute("programari", programari);
        return "programari";
    }

    @GetMapping("/addProgramare")
    public String addProgramare(Model model) {
        model.addAttribute("programare", new ProgramareDto());
        return "addProgramare";
    }
    @PostMapping("/addProgramare")
    public String saveProgramare(@ModelAttribute("programare") ProgramareDto programareDto) {

        programareService.save(programareDto);

        return "redirect:/programari";
    }


    @GetMapping("/programari/delete/{id}")
    public String delete(@PathVariable Long id) {
        programareService.deleteById(id);
        return "redirect:/programari";
    }

//    @GetMapping("/programari/update/{id}")
//    public String updateProgramare(@PathVariable int id, Model model) {
//        Programare dto = programareService.findById(id);
//        model.addAttribute("programare", dto);
//        return "updateProgramare";
//    }

    @GetMapping("/programari/update/{id}")
    public String updateProgramare(@PathVariable int id, Model model) {
        ProgramareDto dto = programareService.findDtoById(id);
        model.addAttribute("programare", dto);
        return "updateProgramare";
    }
    @PostMapping("/programari/update/{id}")
    public String update(@ModelAttribute("programare") ProgramareDto dto) {
        programareService.update(dto);
        return "redirect:/programari";
    }

//    @PostMapping("/programari/update/{id}")
//    public String update(@ModelAttribute Programare tratament) {
//        programareService.update(tratament);
//        return "redirect:/programari";
//    }

}
