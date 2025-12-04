package com.clinica.web.service;

import com.clinica.web.dto.PrescriereTratamentDto;
import com.clinica.web.dto.TratamentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PrescriereTratamentService {
    List<PrescriereTratamentDto> findAllPrescriereTrataments();
    List<PrescriereTratamentDto> search(String keyword, String value);

    void savePrescriere(PrescriereTratamentDto prescriereTratamentDto);

}
