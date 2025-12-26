package com.clinica.web.service.impl;

import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.model.Medicament;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.MedicamentRepository;
import com.clinica.web.service.MedicamentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentRepository medicamentRepository;

    public MedicamentServiceImpl(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<MedicamentDto> findAllMedicaments() {
        return medicamentRepository.findAll()
                .stream()
                .map(this::mapToMedicamentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentDto> search(String field, String value) {
        return medicamentRepository.search(field, value)
                .stream()
                .map(this::mapToMedicamentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Medicament save(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    private MedicamentDto mapToMedicamentDto(Medicament medicament) {
        return MedicamentDto.builder()
                .medicamentID(medicament.getMedicamentID())
                .nume(medicament.getNume())
                .data_expirarii(LocalDate.from(medicament.getData_expirarii()))
                .pret(medicament.getPret())
                .stoc(medicament.getStoc())
                .build();
    }


    @Override
    public void deleteById(Long medicamentId) {
        medicamentRepository.deleteById(medicamentId);
    }
    @Override
    public Medicament findById(Long id) {
        return medicamentRepository.findById(id);
    }

    @Override
    public Medicament update(Medicament pacient) {
        return medicamentRepository.update(pacient);
    }

}
