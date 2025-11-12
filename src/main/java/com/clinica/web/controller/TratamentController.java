package com.clinica.web.controller;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.service.TratamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TratamentController {
    private final TratamentService tratamentService;
    @Autowired
    public TratamentController(TratamentService tratamentService) {
        this.tratamentService = tratamentService;
    }
    @GetMapping("/tratamente")
    public String tratamente(Model model){
        List<TratamentDto> tratamente=tratamentService.findAllTrataments();
        model.addAttribute("tratamente",tratamente);
        return "tratamente";
    }
}
