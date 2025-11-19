package com.clinica.web.service.impl;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientJdbcRepository;
import com.clinica.web.repository.PacientRepository;
import com.clinica.web.service.PacientService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacientServiceImpl implements PacientService {

    private final PacientJdbcRepository pacientRepository;

    public PacientServiceImpl(PacientJdbcRepository pacientRepository) {
        this.pacientRepository = pacientRepository;
    }

    @Override
    public List<PacientDto> findAllPacients() {
        return pacientRepository.findAllPacients().stream()
                .map(this::maptoPacientDto)
                .collect(Collectors.toList());
    }

    private PacientDto maptoPacientDto(Pacient pacient) {
        return PacientDto.builder()
                .PacientID(pacient.getPacientID())
                .Nume(pacient.getNume())
                .Prenume(pacient.getPrenume())
                .Oras(pacient.getOras())
                .Localitate(pacient.getLocalitate())
                .Strada(pacient.getStrada())
                .Sex(pacient.getSex())
                .Data_nasterii(pacient.getData_nasterii())
                .build();
    }
}
