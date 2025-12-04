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
@Table(name = "PrescriereTratament")
public class PrescriereTratament {

    @EmbeddedId
    private PrescriereTratamentId id;

    @Column(name = "Durata")
    private String durata;

    @ManyToOne
    @JoinColumn(name = "TratamentID", insertable = false, updatable = false)
    private Tratament tratament;

    @ManyToOne
    @JoinColumn(name = "ProgramareID", insertable = false, updatable = false)
    private Programare programare;

    public void setProgramare(int programareID) {
        this.programare.setProgramareID(programareID);
    }

}
