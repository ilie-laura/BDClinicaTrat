package com.clinica.web.service;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientJdbcRepository;
import org.springframework.stereotype.Service;
import java.util.List;
//@Service
public interface PacientService {

    public List<Pacient> findAll();
    Pacient save(Pacient pacient);
    void deleteById(Long pacientId);
    Pacient update(Pacient pacient);
    public List<Pacient> search(String field, String value) ;

    Pacient findById(Long id);
}
