package com.clinica.web.service;

import com.clinica.web.dto.PrescriereTratamentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PrescriereTratamentService {
    List<PrescriereTratamentDto> findAllPrescriereTrataments();
}
