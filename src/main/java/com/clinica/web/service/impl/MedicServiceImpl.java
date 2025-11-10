package com.clinica.web.service.impl;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.model.Medic;
import com.clinica.web.repository.MedicRepository;
import com.clinica.web.service.MedicService;

import java.util.List;
import java.util.stream.Collectors;

public class MedicServiceImpl implements MedicService {
    private MedicRepository medicRepository;

    public MedicServiceImpl(MedicRepository medicRepository) {
        this.medicRepository = medicRepository;
    }
    @Override
    public List<MedicDto> findAllMedics(){
        List<Medic> medics=medicRepository.findAll();
        return medics.stream().map((medic)->maptoMedicDto(medic)).collect(Collectors.toList());

    }
    private MedicDto maptoMedicDto(Medic medic){
        MedicDto medicDto=MedicDto.builder()
                .MedicID(medic.getMedicID())
                .Nume(medic.getNume())
                .Prenume(medic.getPrenume())
                .Sex(medic.getSex())
                .Specializare(medic.getSpecializare())
                .build();
return medicDto;
    }
}
