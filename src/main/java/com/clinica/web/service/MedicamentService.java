package com.clinica.web.service;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.model.Medicament;

import java.util.List;

public interface MedicamentService {

    List<MedicamentDto> findAllMedicaments();


    List<MedicamentDto> search(String field, String value);

    Medicament save(Medicament medicament);
}
