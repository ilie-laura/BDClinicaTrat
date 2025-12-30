package com.clinica.web.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Medicament medicament;

public void setMedicamentID(int id){
    this.medicament.setMedicamentId(id);
}
    public void setTratamentID(int id){
        this.tratament.setTratamentId(id);
    }


    public Tratament getTratament() {
        return tratament;
    }

    public void setTratament(Tratament tratament) {
        this.tratament = tratament;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }
}
