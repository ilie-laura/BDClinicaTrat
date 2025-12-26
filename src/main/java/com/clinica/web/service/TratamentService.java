package com.clinica.web.service;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Pacient;
import com.clinica.web.model.Tratament;

import java.util.List;

public interface TratamentService {

    List<TratamentDto> findAllTrataments();

    List<TratamentDto> search(String keyword, String value);

    void saveTratament(TratamentDto tratamentDto);


    void deleteById(Long tratamentId);
    Tratament update(Tratament tratament);
    Tratament findById(Long id);
}
