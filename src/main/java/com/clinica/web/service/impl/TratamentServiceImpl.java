package com.clinica.web.service.impl;

import com.clinica.web.dto.TratamentDto;
import com.clinica.web.model.Tratament;
import com.clinica.web.repository.TratamentRepository;
import com.clinica.web.service.TratamentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TratamentServiceImpl implements TratamentService {

    private final TratamentRepository tratamentRepository;

    public TratamentServiceImpl(TratamentRepository tratamentRepository) {
        this.tratamentRepository = tratamentRepository;
    }

    @Override
    public List<TratamentDto> findAllTrataments() {
        return tratamentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TratamentDto> search(String field, String value) {
        return tratamentRepository.search(field,value)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveTratament(TratamentDto dto) {
        Tratament t = new Tratament();
        t.setNume(dto.getNume());
        t.setDurata_tratament(dto.getDurata_tratament());
        t.setData_inceput(LocalDateTime.now());

        tratamentRepository.saveTratament(t);
    }

    @Override
    public void deleteTratament(int id) {
        tratamentRepository.delete(id);
    }

    private TratamentDto mapToDto(Tratament t) {
        return TratamentDto.builder()
                .TratamentID(t.getTratamentID())
                .Nume(t.getNume())
                .Data_inceput(t.getData_inceput())
                .Durata_tratament(t.getDurata_tratament())
                .build();
    }
}
