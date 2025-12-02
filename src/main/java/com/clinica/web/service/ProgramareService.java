package com.clinica.web.service;

import com.clinica.web.dto.ProgramareDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProgramareService {

    List<ProgramareDto> findAllProgramari();

    ProgramareDto findProgramareById(int id);

    List<ProgramareDto> search(String field, String value);

    void save(ProgramareDto programareDto);
}
