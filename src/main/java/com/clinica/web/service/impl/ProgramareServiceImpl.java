package com.clinica.web.service.impl;

import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.model.Programare;
import com.clinica.web.repository.ProgramareRepository;
import com.clinica.web.service.ProgramareService;

import java.util.List;
import java.util.stream.Collectors;

public class ProgramareServiceImpl implements ProgramareService {
    private ProgramareRepository programareRepository;

    public ProgramareServiceImpl(ProgramareRepository programareRepository) {
        this.programareRepository = programareRepository;
    }
    public List<ProgramareDto> findAllProgramari() {
        List <Programare> programares=programareRepository.findAll();
        return programares.stream().map((programare)->mapToProgramareDto(programare)).collect(Collectors.toList());


    }
    public ProgramareDto mapToProgramareDto(Programare programare){
        ProgramareDto programareDto=ProgramareDto.builder()
                .ProgramareID(programare.getProgramareID())
                .Data_programare(programare.getData_programare())
                .Durata_programare(programare.getDurata_programare())
        .build();
  return  programareDto;
    }
}
