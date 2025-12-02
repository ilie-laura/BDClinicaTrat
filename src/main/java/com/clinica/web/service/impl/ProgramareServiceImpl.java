package com.clinica.web.service.impl;

import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.model.Programare;
import com.clinica.web.repository.ProgramareRepository;
import com.clinica.web.service.ProgramareService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramareServiceImpl implements ProgramareService {

    private final ProgramareRepository programareRepository;

    public ProgramareServiceImpl(ProgramareRepository programareRepository) {
        this.programareRepository = programareRepository;
    }

    // -------------------------------------------------------
    // FIND ALL
    // -------------------------------------------------------
    @Override
    public List<ProgramareDto> findAllProgramari() {
        return programareRepository.findAll()
                .stream()
                .map(this::mapToProgramareDto)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // FIND BY ID
    // -------------------------------------------------------
    @Override
    public ProgramareDto findProgramareById(int id) {
        Optional<Programare> optional = programareRepository.findByProgramareID((long) id);

        return optional.map(this::mapToProgramareDto).orElse(null);
    }


    @Override
    public List<ProgramareDto> search(String field, String value) {

        List<Programare> lista = programareRepository.search(field, value);

        return lista.stream()
                .map(this::mapToProgramareDto)
                .collect(Collectors.toList());
    }


    @Override
    public void save(ProgramareDto dto) {

        Programare p = new Programare();
        p.setPacientID(dto.getPacientID());
        p.setMedicID(dto.getMedicID());
        p.setDataProgramarii(dto.getData_programare());
        p.setDurataProgramare(dto.getDurata_programare());

        programareRepository.save(p);
    }

    // -------------------------------------------------------
    // DTO MAPPER
    // -------------------------------------------------------
    private ProgramareDto mapToProgramareDto(Programare programare) {

        return ProgramareDto.builder()
                .ProgramareID(programare.getProgramareID())
                .pacientID(programare.getPacientID())
                .medicID(programare.getMedicID())
                .Data_programare(programare.getDataProgramarii())
                .Durata_programare(programare.getDurata_programare())
                .build();
    }
}
