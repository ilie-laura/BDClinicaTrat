package com.clinica.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class TratamentMedicatie {
    @EmbeddedId
    private TratamentMedicatieId id;

    private int doza;
    private String frecventa;

    @MapsId("tratamentID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TratamentID", insertable = false, updatable = false)
    private Tratament tratament;

    @MapsId("medicamentID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MedicamentID", insertable = false, updatable = false)
    private Medicament medicament;}
