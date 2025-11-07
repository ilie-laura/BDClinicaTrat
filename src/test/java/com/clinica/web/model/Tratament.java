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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tratament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int TratamentID;
    private String Nume;
    // Relația 1-la-M: Un plan de Tratament include Multe Medicamente Specifice
    @OneToMany(mappedBy = "Tratament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TratamentMedicatie> componente = new HashSet<>();
    // Relația 1-la-M: Un tratament este inclus în Multe Prescrieri
    @OneToMany(mappedBy = "Tratament")
    private Set<PrescriereTratament> prescrise = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime Data_inceput;
    private String Durata_tratament;

}
