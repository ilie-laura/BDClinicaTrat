package com.clinica.web.service.impl;

import com.clinica.web.dto.PacientDto;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientJdbcRepository;

import com.clinica.web.service.PacientService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PacientServiceImpl implements PacientService {

    private final PacientJdbcRepository pacientRepository;

    public PacientServiceImpl(PacientJdbcRepository pacientRepository) {
        this.pacientRepository = pacientRepository;
    }


    @Override
    public List<Pacient> findAll(Boolean dir,String field) {
        return pacientRepository.findAll(dir,field);
    }

    @Override
    public List<Pacient> search(String field, String value,Boolean dir) {
        return pacientRepository.search(field, value,dir);
    }

    private PacientDto maptoPacientDto(Pacient pacient) {
        return PacientDto.builder()
                .PacientID(pacient.getPacientID())
                .Nume(pacient.getNume())
                .Prenume(pacient.getPrenume())
                .Oras(pacient.getOras())
                .Localitate(pacient.getLocalitate())
                .Strada(pacient.getStrada())
                .Sex(pacient.getSex())
                .Data_nasterii(pacient.getData_nasterii().atStartOfDay())
                .build();
    }
    @Override
    public Pacient save(Pacient pacient) {
        return pacientRepository.save(pacient);
    }
    @Override
    public void deleteById(Long pacientId) {
        pacientRepository.deleteById(pacientId);
    }
    @Override
    public Pacient findById(Long id) {
        return pacientRepository.findById(id);
    }

    @Override
    public Pacient update(Pacient pacient) {
        return pacientRepository.update(pacient);
    }
    public String getNumeCompletById(Integer pacientId) {
        Pacient p = pacientRepository.findById(pacientId.longValue());
        return p.getNume() + " " + p.getPrenume();
    }
@Override
public boolean existsByCnp(String cnp){
        return pacientRepository.existsByCnp(cnp);
}

}
