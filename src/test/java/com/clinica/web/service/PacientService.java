package com.clinica.web.service;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Pacient;

import java.util.List;

public interface PacientService {
    List<PacientDto> findAllPacients();
}
