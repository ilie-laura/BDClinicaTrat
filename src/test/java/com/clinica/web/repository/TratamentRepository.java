package com.clinica.web.repository;

import com.clinica.web.model.Tratament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TratamentRepository extends JpaRepository<Tratament,Long> {
    Optional<Tratament> findByTratamentID(int url);
}
