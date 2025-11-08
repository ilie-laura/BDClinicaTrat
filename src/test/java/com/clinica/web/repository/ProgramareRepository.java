package com.clinica.web.repository;

import com.clinica.web.model.Programare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramareRepository extends JpaRepository<Programare,Long> {
    Optional<Programare> findByProgramareIDID(int url);
}
