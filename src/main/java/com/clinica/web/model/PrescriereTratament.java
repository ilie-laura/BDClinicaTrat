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

    // 🌟 Soluția! Definește un ID simplu, auto-generat 🌟
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

//    // 🌟 Cheia Străină 1: Referința la Programare
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ProgramareID", nullable = false)
//    private Programare programare;
////Id
//    // 🌟 Cheia Străină 2: Referința la Tratamentul Prescris
//    @ManyToOne(fetch = FetchType.LAZY)
//   // @JoinColumn(name = "TratamentID", nullable = false)
//    private Tratament Tratament;

}
