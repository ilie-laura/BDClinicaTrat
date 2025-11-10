package com.clinica.web.service;

import com.clinica.web.dto.PacientDto;

import java.util.List;

public interface PacientService {
    List<PacientDto> findAllPacients();
}
