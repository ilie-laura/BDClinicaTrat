package com.clinica.web.service.impl;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientRepository;
import com.clinica.web.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PacientServiceImpl implements PacientService {
    private PacientRepository pacientRepository;
   //@Autowired
    public PacientServiceImpl(PacientRepository pacientRepository) {
        this.pacientRepository = pacientRepository;
    }

    @Override
    public List<PacientDto> findAllPacients() {
        List<Pacient> pacients=pacientRepository.findAll();
        return pacients.stream().map((pacient)->maptoPacientDto(pacient)).collect(Collectors.toList());

    }
    private PacientDto maptoPacientDto(Pacient pacient) {
        PacientDto pacientDto= PacientDto.builder()
                .PacientID(pacient.getPacientID())
                .Nume(pacient.getNume())
                .Prenume(pacient.getPrenume())
                .Oras(pacient.getOras())
                .Localitate(pacient.getLocalitate())
                .Strada(pacient.getStrada())
                .Sex(pacient.getSex())
                .Data_nasterii(pacient.getData_nasterii())
                .build();
        return pacientDto;
    }
}
