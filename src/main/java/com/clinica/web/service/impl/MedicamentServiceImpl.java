package com.clinica.web.service.impl;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.repository.MedicamentRepository;
import com.clinica.web.service.MedicamentService;

import java.util.List;
import java.util.stream.Collectors;

public class MedicamentServiceImpl implements MedicamentService {
    private MedicamentRepository medicamentRepository;
    //@Autowired
    public MedicamentServiceImpl(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<MedicamentDto> findAllMedicaments() {
        List<Medicament> medicaments=medicamentRepository.findAll();
        return medicaments.stream().map((medicament)->maptoMedicamentDto(medicament)).collect(Collectors.toList());

    }
    private MedicamentDto maptoMedicamentDto(Medicament medicament) {
        MedicamentDto medicamentDto= MedicamentDto.builder()
                .MedicamentID(medicament.getMedicamentID())
                .Data_expirarii(medicament.getData_expirarii())
                .Pret(medicament.getPret())
                .Stoc(medicament.getStoc())
                .build();
        return medicamentDto;
    }
}
