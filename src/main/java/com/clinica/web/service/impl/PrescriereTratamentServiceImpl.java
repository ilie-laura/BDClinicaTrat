package com.clinica.web.service.impl;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.repository.PrescriereTratamentRepository;
import com.clinica.web.service.PrescriereTratamentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriereTratamentServiceImpl implements PrescriereTratamentService {

    private final PrescriereTratamentRepository prescriereTratamentRepository;

    public PrescriereTratamentServiceImpl(PrescriereTratamentRepository prescriereTratamentRepository) {
        this.prescriereTratamentRepository = prescriereTratamentRepository;
    }

    @Override
    public List<PrescriereTratamentDto> findAllPrescriereTrataments() {
        List<PrescriereTratament> prescrieri = prescriereTratamentRepository.findAll();

        return prescrieri.stream()
                .map(this::mapToPrescriereTratamentDto)
                .collect(Collectors.toList());
    }

    private PrescriereTratamentDto mapToPrescriereTratamentDto(PrescriereTratament prescriere) {
        return PrescriereTratamentDto.builder()
                .programareID(prescriere.getId().getProgramareID())
                .tratamentID(prescriere.getId().getTratamentID())
                .durata(prescriere.getDurata())
                .build();
    }
}
