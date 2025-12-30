package com.clinica.web.service;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.MedicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//@Service
public interface MedicService {
    //@Override
//  public  List<MedicDto> findAllMedics();


  public List<Medic> findAll(Boolean dir,String field);
  public List<Medic> search(String field, String value,Boolean dir) ;
  Medic save(Medic medic);

    void deleteById(Long medicId);
    Medic update(Medic pacient);
    Medic findById(Long medicId);

    public Map<Integer, Integer> findNrProgramariPerMedic() ;


    Map<Integer, List<String>> findPacientiPerMedic();
}
