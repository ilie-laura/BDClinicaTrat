package com.clinica.web.service;

import com.clinica.web.dto.MedicDto;

import java.util.List;

public interface MedicService {
    List<MedicDto> findAllMedics();
}
