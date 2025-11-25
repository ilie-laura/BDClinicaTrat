package com.clinica.web.service.impl;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.MedicRepository;
import com.clinica.web.service.MedicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
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

    @Override
    public List<Medic> findAll() {
        return medicRepository.findAll();
    }
    @Override
    public List<Medic> search(String field, String value) {
        return medicRepository.search(field, value);
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
    @Override
    public Medic save(Medic medic) {
        return medicRepository.save(medic);
    }
}
