package com.clinica.web.controller;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import com.clinica.web.model.Programare;
import com.clinica.web.model.Tratament;
import com.clinica.web.repository.MedicRepository;
import com.clinica.web.service.MedicService;
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
    private final MedicRepository medicRepository;
    private final PacientService pacientService;
    private final MedicService medicService;

    @Autowired
    public ProgramareController(ProgramareService programareService, MedicRepository medicRepository, PacientService pacientService, MedicService medicService) {
        this.programareService = programareService;
        this.medicRepository = medicRepository;
        this.pacientService = pacientService;
        this.medicService = medicService;
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
        try {
            if (field != null && value != null && !value.isEmpty()) {
                // căutare
                List<ProgramareDto> lista = programareService.search(field, value, currentDir);
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

                programari = programareService.findAll(currentDir, field);
            }
            programari.forEach(p -> {
                p.setPacientNume(
                        pacientService.getNumeCompletById(p.getPacientId())
                );
                p.setMedicNume(
                        medicService.getNumeCompletById(p.getMedicId())
                );
            });
        }catch(Exception e){

            model.addAttribute("errorMessage", "Eroare la procesarea datelor: " + e.getMessage());
            programari = List.of();
        }
        model.addAttribute("programari", programari);
        return "programari";
    }

    @GetMapping("/addProgramare")
    public String addProgramare(Model model) {

        model.addAttribute("programare", new ProgramareDto());

        List<Pacient> pacienti = pacientService.findAll(null,null);
        List<Medic>medici=medicRepository.findAll(null,null);
        model.addAttribute("medici",medici);
        model.addAttribute("pacienti", pacienti);

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
        List<Pacient> pacienti = pacientService.findAll(null,null);
        List<Medic> medici = medicService.findAll(null,null);

        model.addAttribute("pacienti", pacienti);
        model.addAttribute("medici", medici);
        model.addAttribute("programare", dto);

        return "updateProgramare";
    }
    @PostMapping("/programari/update/{id}")
    public String update(@ModelAttribute("programare") ProgramareDto dto) {
        programareService.update(dto);
        return "redirect:/programari";
    }

    public void enrichProgramare(ProgramareDto p) {

        String pacientNume = pacientService.getNumeCompletById(p.getPacientId());
        String medicNume   = medicService.getNumeCompletById(p.getMedicId());

        p.setPacientNume(pacientNume);
        p.setMedicNume(medicNume);
    }
    public void enrichProgramari(List<ProgramareDto> programari) {
        for (ProgramareDto p : programari) {
            enrichProgramare(p);
        }
    }

    @GetMapping("/programari/durata-peste-medie")
    public String programariDurataPesteMedie(Model model) {

        List<ProgramareDto> programari =
                programareService.findWithDurataAboveAverage();

        enrichProgramari(programari);

        model.addAttribute("programari", programari);
        model.addAttribute("durataMedie", true);

        return "programari";
    }
    @GetMapping("/programari/an")
    public String programariDinAn(@RequestParam int an, Model model) {

        List<ProgramareDto> programari =
                programareService.findProgramariDinAn(an);

        model.addAttribute("programari", programari);
        model.addAttribute("anSelectat", an);

        return "programari";
    }

}
