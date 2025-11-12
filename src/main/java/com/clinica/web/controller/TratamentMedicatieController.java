package com.clinica.web.controller;
import com.clinica.web.dto.TratamentMedicatieDto;
import org.springframework.ui.Model;
import com.clinica.web.service.TratamentMedicatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TratamentMedicatieController {
    private final TratamentMedicatieService service;

    @Autowired
    public TratamentMedicatieController(TratamentMedicatieService service) {
        this.service = service;
    }
    @GetMapping("/tratament_medicatie")
    public String tratamentMedicatie(Model model){
        List<TratamentMedicatieDto> tratamentMedicatie= service.findAllTratamentMedicaties();
        System.out.println(service.findAllTratamentMedicaties().size());
        model.addAttribute("tratamentMedicatie",tratamentMedicatie);
        return "tratament_medicatie";
    }
}
