package com.clinica.web.service.impl;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.model.Medic;
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

//    public List<MedicDto> findAllMedics(){
//        List<Medic> medics=medicRepository.findAll();
//        return medics.stream().map((medic)->maptoMedicDto(medic)).collect(Collectors.toList());
//
//    }

    @Override
    public List<Medic> findAll(Boolean dir,String field) {
        return medicRepository.findAll(dir,field);
    }
    @Override
    public List<Medic> search(String field, String value,Boolean dir) {
        return medicRepository.search(field, value,dir);
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


    @Override
    public void deleteById(Long medicId) {
        medicRepository.deleteById(medicId);
    }



    @Override
    public Medic update(Medic medic) {
        return medicRepository.update(medic);
    }

    @Override
    public Medic findById(Long medicId) {
        return medicRepository.findById(medicId);
    }
}
