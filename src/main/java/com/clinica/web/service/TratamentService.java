package com.clinica.web.service;

import com.clinica.web.dto.TratamentDto;

import java.util.List;

public interface TratamentService {

    List<TratamentDto> findAllTrataments();

    List<TratamentDto> search(String keyword, String value);

    void saveTratament(TratamentDto tratamentDto);

    void deleteTratament(int id);
}
