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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private int Doza;
    private String Frecventa;
    // Cheia Străină 1: Referința la Planul de Tratament
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TratamentID", nullable = false)
    private Tratament Tratament;

    // Cheia Străină 2: Referința la Medicamentul Specific
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MedicamentID", nullable = false)
    private Medicament medicament;
}
