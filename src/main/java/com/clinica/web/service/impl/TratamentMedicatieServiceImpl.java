package com.clinica.web.service.impl;

import com.clinica.web.dto.TratamentMedicatieDto;
import com.clinica.web.model.TratamentMedicatie;
import com.clinica.web.repository.TratamentMedicatieRepository;
import com.clinica.web.service.TratamentMedicatieService;

import java.util.List;
import java.util.stream.Collectors;

public class TratamentMedicatieServiceImpl implements TratamentMedicatieService {
    private TratamentMedicatieRepository tratamentMedicatieRepository;

    public TratamentMedicatieServiceImpl(TratamentMedicatieRepository tratamentMedicatieRepository) {
        this.tratamentMedicatieRepository = tratamentMedicatieRepository;
    }

    @Override
    public List<TratamentMedicatieDto> findAllTratamentMedicaties() {
        List<TratamentMedicatie>  tratamentMedicaties=tratamentMedicatieRepository.findAll();
        return tratamentMedicaties.stream().map((tratamentMedicatie)->mapToTratamentMedicatieDto(tratamentMedicatie)).collect(Collectors.toList());
    }
    public TratamentMedicatieDto mapToTratamentMedicatieDto(TratamentMedicatie tratamentMedicatie){
        TratamentMedicatieDto tratamentMedicatieDto=TratamentMedicatieDto.builder()
                .id(tratamentMedicatie.getId())
                .Doza(tratamentMedicatie.getDoza())
                .Frecventa(tratamentMedicatie.getFrecventa())
                .build();
        return tratamentMedicatieDto;
    }
}
