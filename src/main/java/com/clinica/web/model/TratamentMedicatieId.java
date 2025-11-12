package com.clinica.web.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class TratamentMedicatieId implements Serializable {
    @Column(name = "TratamentID")
    private long tratamentID;
    @Column(name = "MedicamentID")
    private long medicamentID;
}
