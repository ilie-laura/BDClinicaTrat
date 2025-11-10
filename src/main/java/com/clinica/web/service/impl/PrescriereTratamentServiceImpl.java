package com.clinica.web.service.impl;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.repository.PrescriereTratamentRepository;
import com.clinica.web.service.PrescriereTratamentService;

import java.util.List;
import java.util.stream.Collectors;

public class PrescriereTratamentServiceImpl implements PrescriereTratamentService {
    private PrescriereTratamentRepository prescriereTratamentRepository;

    public PrescriereTratamentServiceImpl(PrescriereTratamentRepository prescriereTratamentRepository) {
        this.prescriereTratamentRepository = prescriereTratamentRepository;
    }
    @Override
    public List<PrescriereTratamentDto> findAllPrescriereTrataments() {
        List<PrescriereTratament> prescrieri=prescriereTratamentRepository.findAll();
        return prescrieri.stream().map((prescriere)->maptoPrescriereTratamentDto(prescriere)).collect(Collectors.toList());

    }
    public PrescriereTratamentDto maptoPrescriereTratamentDto(PrescriereTratament prescrieri) {
        PrescriereTratamentDto prescriereTratamentDto= PrescriereTratamentDto.builder()
                .id(prescrieri.getId())
                .Durata(prescrieri.getDurata())
                .build();
        return prescriereTratamentDto;
    }
}
