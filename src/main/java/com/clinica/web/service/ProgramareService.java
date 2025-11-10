package com.clinica.web.service;

import com.clinica.web.dto.ProgramareDto;

import java.util.List;

public interface ProgramareService {
    List<ProgramareDto> findAllProgramari();
}
