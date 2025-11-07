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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Medic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int MedicID;
    private String Nume;
    private String Prenume;
    private String Specializare;
    private long Salariu;
    private char Sex;
    private String CNP;
    @OneToMany(mappedBy = "medic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Programare> consultatii = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime Data_angajarii;
}
