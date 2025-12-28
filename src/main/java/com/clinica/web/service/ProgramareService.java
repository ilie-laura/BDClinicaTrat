package com.clinica.web.service;

import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.model.Programare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProgramareService {

    List<ProgramareDto> findAll(Boolean dir,String field);
   Programare findById(long id);
    List<ProgramareDto> search(String field, String value,Boolean dir);
    void save(ProgramareDto programareDto);

    void deleteById(Long id);

    public void update(ProgramareDto dto);
//
   ProgramareDto findDtoById(int id);
}
