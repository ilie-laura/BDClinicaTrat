package com.clinica.web.service;

import com.clinica.web.dto.MedicamentDto;

import java.util.List;

public interface MedicamentService {
    List<MedicamentDto> findAllMedicaments();
}
