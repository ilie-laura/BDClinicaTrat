package com.clinica.web.service;

import com.clinica.web.dto.TratamentDto;

import java.util.List;

public interface TratamentService {
    List<TratamentDto> findAllTrataments();
}
