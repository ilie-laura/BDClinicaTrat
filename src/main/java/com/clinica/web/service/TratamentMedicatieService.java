package com.clinica.web.service;

import com.clinica.web.model.TratamentMedicatie;

import java.util.List;

public interface TratamentMedicatieService {
   public List<TratamentMedicatie> findAll(Boolean dir,String field);
    public List<TratamentMedicatie> search(String field, String value, Boolean dir);
    TratamentMedicatie save(TratamentMedicatie tratamentMedicatie);
    void deleteById(Long tratamentId,Long medicamentId);
    TratamentMedicatie update(TratamentMedicatie tratamentMedicatie);
    TratamentMedicatie findById(Long id,Long id2);
}
