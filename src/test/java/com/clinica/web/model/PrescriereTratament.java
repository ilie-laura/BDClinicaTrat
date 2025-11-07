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
public class PrescriereTratament {

    // 🌟 Soluția! Definește un ID simplu, auto-generat 🌟
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sau .SEQUENCE, depinde de BD (IDENTITY e bună pentru SQL Server/MySQL)
    private Long id;
    private String Durata;
    // 🌟 Cheia Străină 1: Referința la Programare
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProgramareID", nullable = false)
    private Programare Programare;

    // 🌟 Cheia Străină 2: Referința la Tratamentul Prescris
    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "TratamentID", nullable = false)
    private Tratament Tratament;

}
