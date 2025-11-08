package com.clinica.web.repository;

import com.clinica.web.model.TratamentMedicatie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TratamentMedicatieRepository extends JpaRepository<TratamentMedicatie,Long> {
  //  @Override
    Optional<TratamentMedicatieRepository> findByid(int url);
}
