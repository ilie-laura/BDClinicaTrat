package com.clinica.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Programare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programareID;
    // Relația M-la-1: O consultatie are UN singur Medic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MedicID", nullable = false)
    private Medic medic;

    // Relația M-la-1: O consultatie are UN singur Pacient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PacientID", nullable = false)
    private Pacient pacient;

    @OneToMany(mappedBy = "programare", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrescriereTratament> prescrise = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime Data_programare;
    private String Durata_programare;

    public void setPacientID(int pacientID) {
        if (this.pacient == null) {
            this.pacient = new Pacient();
        }
        this.pacient.setPacientID(pacientID);
    }

    public void setMedicID(int medicID) {
        if (this.medic == null) {
            this.medic = new Medic();
        }
        this.medic.setMedicID(medicID);
    }
    public void setDataProgramarii(LocalDateTime data) {
        this.Data_programare = data;
    }


    public void setDurataProgramare(String durataProgramare) {
        this.Durata_programare = durataProgramare;
    }
}
