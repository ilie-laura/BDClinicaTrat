package com.clinica.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicamentID;
    private String Nume;
    private int Stoc;
    private int Pret;
    private LocalDateTime Data_expirarii;
    // Relația 1-la-M: Un Medicament este parte din Multe structuri de Tratament
    @OneToMany(mappedBy = "medicament")
    private Set<TratamentMedicatie> inclusInTratamente = new HashSet<>();
}
