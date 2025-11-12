package com.clinica.web.repository;

import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.model.TratamentMedicatie;
import com.clinica.web.model.TratamentMedicatieId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TratamentMedicatieRepository extends JpaRepository<TratamentMedicatie, TratamentMedicatieId> {
  //  @Override
  List<TratamentMedicatie> findByIdTratamentID(long tratamentID);
  List<TratamentMedicatie> findByIdMedicamentID(long medicamentID);
}
