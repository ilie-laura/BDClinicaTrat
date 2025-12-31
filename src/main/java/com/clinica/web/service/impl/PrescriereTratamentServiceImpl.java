package com.clinica.web.service.impl;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.dto.ProgramareDto;

import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.model.PrescriereTratamentId;

import com.clinica.web.repository.PrescriereTratamentRepository;
import com.clinica.web.service.PrescriereTratamentService;
import com.clinica.web.service.ProgramareService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriereTratamentServiceImpl implements PrescriereTratamentService {

    private final PrescriereTratamentRepository prescriereTratamentRepository;
    private final ProgramareService programareService;

    public PrescriereTratamentServiceImpl(PrescriereTratamentRepository prescriereTratamentRepository, ProgramareService programareService) {
        this.prescriereTratamentRepository = prescriereTratamentRepository;
        this.programareService = programareService;
    }

    @Override
    public List<PrescriereTratamentDto> findAllPrescriereTrataments() {
        List<PrescriereTratament> prescrieri = prescriereTratamentRepository.findAll();

        return prescrieri.stream()
                .map(this::mapToPrescriereTratamentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriereTratamentDto> search(String field, String value) {
        return prescriereTratamentRepository.search(field,value)
                .stream()
                .map(this::mapToPrescriereTratamentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void savePrescriere(PrescriereTratamentDto dto) {
        PrescriereTratament t = new PrescriereTratament();
        PrescriereTratamentId id = new PrescriereTratamentId();

        if (prescriereTratamentRepository.findByProgramareID(dto.getProgramareID()).isEmpty()) {
            throw new IllegalArgumentException("Programare ID nu există!");
        }

        if (prescriereTratamentRepository.findByTratamentID(dto.getTratamentID()).isEmpty()) {
            throw new IllegalArgumentException("Tratament ID nu există!");
        }
        id.setProgramareID(dto.getProgramareID());
        id.setTratamentID(dto.getTratamentID());

        t.setId(id);
        t.setDurata(dto.getDurata());

        prescriereTratamentRepository.savePrescriere(t);
    }

    private PrescriereTratamentDto mapToPrescriereTratamentDto(PrescriereTratament prescriere) {
        return PrescriereTratamentDto.builder()
                .programareID(prescriere.getId().getProgramareID())
                .tratamentID(prescriere.getId().getTratamentID())
                .durata(prescriere.getDurata())
                .build();
    }

    public String getProgramareInfo(Long programareId) {
        ProgramareDto p = programareService.findDtoById(programareId.intValue());
        programareService.enrichProgramare(p);
        return  p.getMedicNume() +
                " – " + p.getDataProgramare().toLocalDate();
    }

}
