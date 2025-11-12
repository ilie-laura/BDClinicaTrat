package com.clinica.web.service.impl;

import com.clinica.web.dto.TratamentMedicatieDto;
import com.clinica.web.model.TratamentMedicatie;
import com.clinica.web.repository.TratamentMedicatieRepository;
import com.clinica.web.service.TratamentMedicatieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TratamentMedicatieServiceImpl implements TratamentMedicatieService {
    private final TratamentMedicatieRepository tratamentMedicatieRepository;

    public TratamentMedicatieServiceImpl(TratamentMedicatieRepository tratamentMedicatieRepository) {
        this.tratamentMedicatieRepository = tratamentMedicatieRepository;
    }

    @Override
    public List<TratamentMedicatieDto> findAllTratamentMedicaties() {
        List<TratamentMedicatie> tratamentMedicaties = tratamentMedicatieRepository.findAll();
        return tratamentMedicaties.stream()
                .map(this::mapToTratamentMedicatieDto)
                .collect(Collectors.toList());
    }

    private TratamentMedicatieDto mapToTratamentMedicatieDto(TratamentMedicatie tratamentMedicatie) {
        return TratamentMedicatieDto.builder()
                .tratamentID(tratamentMedicatie.getTratament().getTratamentID())
                .medicamentID(tratamentMedicatie.getMedicament().getMedicamentID())
                .Doza(tratamentMedicatie.getDoza())
                .Frecventa(tratamentMedicatie.getFrecventa())
                .build();
    }
}
