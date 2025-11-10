package com.clinica.web.repository;

import com.clinica.web.model.Medic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicRepository extends JpaRepository<Medic,Long> {
    //@Override
    Optional<Medic> findByMedicID(int url);
}
