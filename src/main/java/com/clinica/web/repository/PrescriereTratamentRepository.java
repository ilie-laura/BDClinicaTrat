package com.clinica.web.repository;

import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.model.PrescriereTratamentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriereTratamentRepository extends JpaRepository<PrescriereTratament, PrescriereTratamentId> {
    //@Override
   // Optional<PrescriereTratament> findById(int url);
    List<PrescriereTratament> findByIdProgramareID(Long programareID);
    List<PrescriereTratament> findByIdTratamentID(Long tratamentID);
}
