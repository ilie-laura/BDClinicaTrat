package com.clinica.web.controller;

import com.clinica.web.repository.RapoarteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rapoarte")
public class RapoarteController {

    private final RapoarteRepository rapoarteRepository;

    public RapoarteController(RapoarteRepository rapoarteRepository) {
        this.rapoarteRepository = rapoarteRepository;
    }

    @GetMapping
    public String rapoarte(@RequestParam(required = false) Double minCheltuieli,
                           @RequestParam(required = false,defaultValue = "15") Integer pragStoc,
                           Model model) {

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
        List<Map<String, Object>> cheltuieli = rapoarteRepository.pacientiCuCheltuieliPesteMedie(minCheltuieli);

        model.addAttribute("pacientiCheltuieli", cheltuieli);
        model.addAttribute("minCheltuieliSelectat", minCheltuieli);

        List<Map<String, Object>> venituriMed = rapoarteRepository.venituriMedicamente();
        model.addAttribute("venituriMedicamente", venituriMed);

        Double totalVenit = rapoarteRepository.getTotalVenit();
        model.addAttribute("totalVenit", totalVenit != null ? totalVenit : 0);

        model.addAttribute("nrProgramariNoi", rapoarteRepository.getNrProgramariNoi());

        int pragAles = pragStoc;
        model.addAttribute("medicamenteStocMic", rapoarteRepository.getNrMedicamenteStocCritic(pragAles));
        model.addAttribute("pragSelectat", pragStoc);
        return "rapoarte";
    }



}
