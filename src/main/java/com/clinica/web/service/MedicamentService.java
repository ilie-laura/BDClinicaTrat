package com.clinica.web.service;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.model.Pacient;

import java.util.List;

public interface MedicamentService {

    List<MedicamentDto> findAll(Boolean dir,String field);
    List<MedicamentDto> search(String field, String value,Boolean dir);
    Medicament save(Medicament medicament);
    void deleteById(Long medicamentId);
    Medicament update(Medicament medicament);
    Medicament findById(Long id);
}
