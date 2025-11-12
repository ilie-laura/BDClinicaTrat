package com.clinica.web.service;

import com.clinica.web.dto.MedicDto;
import org.springframework.stereotype.Service;

import java.util.List;
//@Service
public interface MedicService {
    //@Override
    List<MedicDto> findAllMedics();
}
