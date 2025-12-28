package com.clinica.web.controller;

import com.clinica.web.dto.MedicamentDto;
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
    public String tratamente(@RequestParam(required = false) String field,
                             @RequestParam(required = false) String value,
                             @RequestParam(required = false) Boolean dir,
                             @RequestParam(required = false) String order,
                             Model model){
        boolean currentDir = (dir == null) ? true : dir;
        if (order != null) {
            currentDir = !currentDir;
        }

        boolean nextDir = !currentDir;
        model.addAttribute("dir", currentDir);
        model.addAttribute("nextDir", nextDir);
        model.addAttribute("field", field);

        List<TratamentDto> tratamente ;
        if (field != null && value != null && !value.isEmpty()) {
            // căutare
            List<TratamentDto> lista = tratamentService.search(field, value,currentDir);
            tratamente = lista.stream()
                    .map(m -> TratamentDto.builder()
                            .Nume(m.getNume())
                             .Data_inceput(m.getData_inceput())
                            .Durata_tratament(m.getDurata_tratament())
                            .build()
                    ).toList();

        } else {

            tratamente = tratamentService.findAll(currentDir,field);
        }
        model.addAttribute("tratamente", tratamente);
        return "tratamente";
    }

    // Formular adaugare
    @GetMapping("/addTratament")
    public String addTratament(Model model) {
        model.addAttribute("tratament", new TratamentDto());
        return "addTratament";
    }

    // Salvare tratament
    @PostMapping("/addTratament")
    public String saveTratament(@ModelAttribute("tratament") TratamentDto dto) {
        tratamentService.saveTratament(dto);
        return "redirect:/tratamente";
    }

    // Căutare
//    @GetMapping("/tratamente/search")
//    public String searchTratamente(@RequestParam String field,
//                                   @RequestParam String value,
//                                   Model model) {
//        System.out.println("Căutare tratament: field=" + field + "  value=" + value);
//        List<TratamentDto> tratamente;
//        if (value != null && !value.trim().isEmpty() && field != null) {
//
//            tratamente = tratamentService.search(field, value,current);
//        } else {
//
//            tratamente = tratamentService.findAllTrataments();
//        }
//
//        model.addAttribute("tratamente", tratamente);
//        return "tratamente";
//    }


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
