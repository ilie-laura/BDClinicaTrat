package com.clinica.web.controller;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.service.MedicamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class MedicamentController {
    private final MedicamentService medicamentService;
    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }
    @GetMapping("/medicaments")
    public String medicaments(@RequestParam(required = false) String field,
                              @RequestParam(required = false) String value,
                              Model model) {

        List<MedicamentDto> medicamente;

        if (field != null && value != null && !value.isEmpty()) {
            // căutare
            List<MedicamentDto> lista = medicamentService.search(field, value);
            medicamente = lista.stream()
                    .map(m -> MedicamentDto.builder()
                            .medicamentID(m.getMedicamentID())
                            .nume(m.getNume())
                            .data_expirarii(m.getData_expirarii())
                            .stoc(m.getStoc())
                            .pret(m.getPret())
                            .build()
                    ).toList();

        } else {

            medicamente = medicamentService.findAllMedicaments();
        }

        model.addAttribute("medicaments", medicamente);
        return "medicaments";
    }

    // Afișează formularul de adăugare medicament
    @GetMapping("/addMedicament")
    public String showAddForm(Model model) {
        model.addAttribute("medicament", new Medicament()); // obiect gol pentru form
        return "addMedicament";
    }

    // Salvează medicamentul nou
    @PostMapping("/addMedicament")
    public String addMedicament(@ModelAttribute Medicament medicament) {
        medicamentService.save(medicament);
        return "redirect:/medicaments"; // după inserare, duce înapoi la listă
    }

}
