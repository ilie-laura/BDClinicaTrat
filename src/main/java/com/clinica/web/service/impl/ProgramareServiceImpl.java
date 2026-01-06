package com.clinica.web.service.impl;

import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.model.Programare;
import com.clinica.web.repository.ProgramareRepository;
import com.clinica.web.service.MedicService;
import com.clinica.web.service.PacientService;
import com.clinica.web.service.ProgramareService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramareServiceImpl implements ProgramareService {

    private final ProgramareRepository programareRepository;
    private final PacientService pacientService;
    private final MedicService medicService;

    public ProgramareServiceImpl(ProgramareRepository programareRepository, PacientService pacientService, MedicService medicService) {
        this.programareRepository = programareRepository;
        this.pacientService = pacientService;
        this.medicService = medicService;
    }


    @Override
    public List<ProgramareDto> findAll(Boolean dir,String field) {
        return programareRepository.findAll(dir,field)
                .stream()
                .map(this::mapToProgramareDto)
                .collect(Collectors.toList());
    }


    @Override
    public Programare findById(long id) {
       return programareRepository.findById(id);
    }
@Override
    public void deleteById(Long programareId) {
        programareRepository.deleteById(programareId);
    }


    public void update(ProgramareDto dto) {
        Programare p = new Programare();
        p.setProgramareID(dto.getProgramareId());
        p.setPacientID(dto.getPacientId());
        p.setMedicID(dto.getMedicId());
        p.setDataProgramarii(dto.getDataProgramare());
        p.setDurataProgramare(dto.getDurataProgramare());

        programareRepository.update(p);
    }

    @Override
    public List<ProgramareDto> search(String field, String value,Boolean dir) {

        List<Programare> lista = programareRepository.search(field, value,dir);

        return lista.stream()
                .map(this::mapToProgramareDto)
                .collect(Collectors.toList());
    }


    @Override
    public void save(ProgramareDto dto) {

        Programare p = new Programare();
        p.setPacientID(dto.getPacientId());
        p.setMedicID(dto.getMedicId());
        p.setDataProgramarii(dto.getDataProgramare());
        p.setDurataProgramare(dto.getDurataProgramare());

        programareRepository.save(p);
    }

    // -------------------------------------------------------
    // DTO MAPPER
    // -------------------------------------------------------
    private ProgramareDto mapToProgramareDto(Programare programare) {

        return ProgramareDto.builder()
                .programareId(programare.getProgramareID())
                .pacientId(programare.getPacientID())
                .medicId(programare.getMedicID())
                .dataProgramare(programare.getDataProgramarii())
                .durataProgramare(programare.getDurata_programare())
                .build();
    }
    @Override
    public ProgramareDto findDtoById(int id) {
        Programare p = programareRepository.findById((long) id);

        return ProgramareDto.builder()
                .programareId(p.getProgramareID())
                .pacientId(p.getPacientID())
                .medicId(p.getMedicID())
                .dataProgramare(p.getData_programare())
                .durataProgramare(p.getDurata_programare())
                .build();
    }
    @Override
    public void creeazaProgramare(String cnp,
                                  int medicID,
                                  LocalDate appointmentDate,
                                  String motiv) {

        int durata = durataDupaMotiv(motiv);

        programareRepository.creeazaProgramareDupaCNP(
                cnp, medicID, appointmentDate, durata, motiv
        );
    }

    private int durataDupaMotiv(String motiv) {
        return switch (motiv) {
            case "Consultatie scurta" -> 10;
            case "Consultatie standard" -> 20;
            case "Control" -> 15;
            case "Investigatie" -> 30;
            default -> throw new IllegalArgumentException("Motiv invalid");
        };
    }
    public void enrichProgramare(ProgramareDto p) {


        String pacientNume = pacientService.getNumeCompletById(p.getPacientId());
        String medicNume   = medicService.getNumeCompletById(p.getMedicId());

        p.setPacientNume(pacientNume);
        p.setMedicNume(medicNume);
    }
    public List<ProgramareDto> findWithDurataAboveAverage(){
        return programareRepository.findWithDurataAboveAverage()
                .stream()
                .map(p -> ProgramareDto.builder()
                        .programareId(p.getProgramareID())
                        .pacientId(p.getPacientID())
                        .medicId(p.getMedicID())
                        .dataProgramare(p.getDataProgramarii())
                        .durataProgramare(p.getDurata_programare())
                        .build()
                )
                .toList();
    }
    public List<ProgramareDto> findProgramariDinAn(int an) {
        return programareRepository.findProgramariDinAn(an);
    }

}
