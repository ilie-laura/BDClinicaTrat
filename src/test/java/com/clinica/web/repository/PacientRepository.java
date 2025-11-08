package com.clinica.web.repository;

import com.clinica.web.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacientRepository extends JpaRepository<Pacient, Long> {
    //@Override
    Optional<Pacient> findByPacientID(int url);
}
