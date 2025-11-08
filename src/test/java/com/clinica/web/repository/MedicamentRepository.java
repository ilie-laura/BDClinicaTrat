package com.clinica.web.repository;

import com.clinica.web.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicamentRepository extends JpaRepository<Medicament,Long> {
 //   @Override
    Optional<Medicament> findByMedicamentID(int url);
}
