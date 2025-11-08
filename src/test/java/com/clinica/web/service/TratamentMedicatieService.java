package com.clinica.web.service;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.dto.TratamentMedicatieDto;

import java.util.List;

public interface TratamentMedicatieService {
    List<TratamentMedicatieDto> findAllTratamentMedicaties();
}
