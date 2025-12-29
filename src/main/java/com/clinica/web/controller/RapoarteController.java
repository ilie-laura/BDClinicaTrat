package com.clinica.web.controller;

import com.clinica.web.repository.RapoarteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rapoarte")
public class RapoarteController {

    private final RapoarteRepository rapoarteRepository;

    public RapoarteController(RapoarteRepository rapoarteRepository) {
        this.rapoarteRepository = rapoarteRepository;
    }

    @GetMapping
    public String rapoarte(Model model) {

        model.addAttribute("mediciSalariu",
                rapoarteRepository.mediciCuSalariuPesteMedie());

        model.addAttribute("pacientiCuProgramari",
                rapoarteRepository.pacientiCuProgramari());

        model.addAttribute("medicamenteFolosite",
                rapoarteRepository.medicamenteFolositeInTratamente());

        model.addAttribute("mediciFaraProgramari",
                rapoarteRepository.mediciFaraProgramari());

        model.addAttribute("salariiJoin",
                rapoarteRepository.salariiMediciCuProgramari(0));
        return "rapoarte";
    }


}
