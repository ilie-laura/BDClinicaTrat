package com.clinica.web.service;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientJdbcRepository;
import org.springframework.stereotype.Service;
import java.util.List;
//@Service
public interface PacientService {
    public List<Pacient> search(String field, String value,Boolean dir) ;
    public List<Pacient> findAll(Boolean dir,String field);
    Pacient save(Pacient pacient);
    void deleteById(Long pacientId);
    Pacient update(Pacient pacient);
    Pacient findById(Long id);
    private int durataDupaMotiv(String motiv) {
        return switch (motiv) {
            case "Consultatie scurta" -> 10;
            case "Consultatie standard" -> 20;
            case "Control" -> 15;
            case "Investigatie" -> 30;
            default -> throw new IllegalArgumentException("Motiv invalid");
        };
    }

    public String getNumeCompletById(Integer pacientId) ;
    public boolean existsByCnp(String cnp);
}
