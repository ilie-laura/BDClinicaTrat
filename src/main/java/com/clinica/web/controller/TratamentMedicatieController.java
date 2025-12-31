package com.clinica.web.controller;
import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.dto.TratamentDto;
import com.clinica.web.dto.TratamentMedicatieDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.model.Pacient;
import com.clinica.web.model.Tratament;
import com.clinica.web.model.TratamentMedicatie;
import com.clinica.web.service.MedicamentService;
import com.clinica.web.service.TratamentService;
import org.springframework.ui.Model;
import com.clinica.web.service.TratamentMedicatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class TratamentMedicatieController {
    private final TratamentMedicatieService tratamentMedicatieService;
    private final TratamentService tratamentService;
    private final MedicamentService medicamentService;

    @Autowired
    public TratamentMedicatieController(TratamentMedicatieService tratamentMedicatieService, TratamentService tratamentService, MedicamentService medicamentService) {
        this.tratamentMedicatieService = tratamentMedicatieService;
        this.tratamentService = tratamentService;
        this.medicamentService = medicamentService;
    }

    @GetMapping("/tratament_medicatie")
    public String tratamentMedicatie( @RequestParam(required = false) String field,
                                      @RequestParam(required = false) String value,
                                      @RequestParam(required = false) Boolean dir,
                                      @RequestParam(required = false) String order,
                                      Model model)
    {
        boolean currentDir = (dir == null) ? true : dir;
        if (order != null) {
            currentDir = !currentDir;
        }

        boolean nextDir = !currentDir;
        model.addAttribute("dir", currentDir);
        model.addAttribute("nextDir", nextDir);

        List<TratamentMedicatie> tratamentMedicatie;

        if (value != null && !value.isEmpty()) {
            tratamentMedicatie = tratamentMedicatieService.search(field, value, currentDir);
        } else {
            tratamentMedicatie = tratamentMedicatieService.findAll(currentDir,field);
        }
        model.addAttribute("tratamentMedicatie",tratamentMedicatie);
        return "tratament_medicatie";
    }


    @GetMapping("/addTratMed")
    public String showAddForm(Model model) {
        TratamentMedicatie tm = new TratamentMedicatie();
        tm.setTratament(new Tratament());
        tm.setMedicament(new Medicament());

        model.addAttribute("tratamentMedicatie", tm);

        List<TratamentDto> tratamente = tratamentService.findAll(null, null);
        List<MedicamentDto> medicamente = medicamentService.findAll(null, null);

        model.addAttribute("tratamente", tratamente);
        model.addAttribute("medicamente", medicamente);
        return "addTratMed";
    }
    @PostMapping("/addTratMed")
    public String addTratMed(@ModelAttribute TratamentMedicatie pacient) {
        tratamentMedicatieService.save(pacient);
        return "redirect:/tratament_medicatie";
    }


    @GetMapping("/tratament_medicatie/delete/{id}/{id2}")
    public String delete(@PathVariable Long id,@PathVariable long id2) {
        tratamentMedicatieService.deleteById(id,id2);
        return "redirect:/tratament_medicatie";
    }

    @GetMapping("/tratament_medicatie/update/{id}/{id2}")
    public String updateTratMed(@PathVariable Long id,@PathVariable Long id2, Model model) {
        TratamentMedicatie p =tratamentMedicatieService.findById(id,id2);
        model.addAttribute("tratamentMedicatie", p);
        return "updateTratMed";
    }


    @PostMapping("/tratament_medicatie/update")
    public String update(@ModelAttribute TratamentMedicatie pacient) {
        tratamentMedicatieService.update(pacient);
        return "redirect:/tratament_medicatie";
    }
}
