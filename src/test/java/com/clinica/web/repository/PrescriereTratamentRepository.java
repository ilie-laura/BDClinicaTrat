package com.clinica.web.repository;

import com.clinica.web.model.PrescriereTratament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriereTratamentRepository extends JpaRepository<PrescriereTratament,Long> {
    //@Override
    Optional<PrescriereTratament> findByProgramareID(int url);
}
