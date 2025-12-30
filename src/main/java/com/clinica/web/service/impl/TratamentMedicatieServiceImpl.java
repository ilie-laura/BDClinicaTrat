package com.clinica.web.service.impl;

import com.clinica.web.dto.TratamentMedicatieDto;
import com.clinica.web.model.Pacient;
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
    public List<TratamentMedicatie> findAll(Boolean dir,String field) {
       return tratamentMedicatieRepository.findAll(dir,field);
    }

    @Override
    public List<TratamentMedicatie> search(String field, String value, Boolean dir) {
        return tratamentMedicatieRepository.search(field, value,dir);
    }

    private TratamentMedicatieDto mapToTratamentMedicatieDto(TratamentMedicatie tratamentMedicatie) {
        return TratamentMedicatieDto.builder()
                .tratamentID(tratamentMedicatie.getTratament().getTratamentId())
                .medicamentID(tratamentMedicatie.getMedicament().getMedicamentId())
                .Doza(tratamentMedicatie.getDoza())
                .Frecventa(tratamentMedicatie.getFrecventa())
                .build();
    }


    @Override
    public TratamentMedicatie save(TratamentMedicatie pacient) {
        return tratamentMedicatieRepository.save(pacient);
    }
    @Override
    public void deleteById(Long pacientId,Long  medicamentId) {
        tratamentMedicatieRepository.deleteById(pacientId,medicamentId);
    }
    @Override
    public TratamentMedicatie findById(Long id,Long id2) {
        return tratamentMedicatieRepository.findById(id,id2);
    }

    @Override
    public TratamentMedicatie update(TratamentMedicatie pacient) {
        return tratamentMedicatieRepository.update(pacient);
    }

}
