package com.clinica.web.service.impl;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Tratament;
import com.clinica.web.repository.TratamentRepository;
import com.clinica.web.service.TratamentService;

import java.util.List;
import java.util.stream.Collectors;

public class TratamentServiceImpl implements TratamentService {
    private TratamentRepository tratamentRepository;

    public TratamentServiceImpl(TratamentRepository tratamentRepository) {
        this.tratamentRepository = tratamentRepository;
    }

    @Override
    public List<TratamentDto> findAllTrataments() {
        List<Tratament> trataments=tratamentRepository.findAll();
        return trataments.stream().map((tratament) -> maptoTratamentDto(tratament)).collect(Collectors.toList());

    }
    private TratamentDto  maptoTratamentDto(Tratament tratament) {
        TratamentDto tratamentDto=TratamentDto.builder()
                .TratamentID(tratament.getTratamentID())
                .Nume(tratament.getNume())
                .Data_inceput(tratament.getData_inceput())
                .Durata_tratament(tratament.getDurata_tratament())
                .build();
        return tratamentDto;

    }
}

