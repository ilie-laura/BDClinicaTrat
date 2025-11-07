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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Table(name="Pacient")
public class Pacient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PacientID;
   private String Nume;
   private String Prenume;
   private String Oras;
    private String Strada;
   private String Localitate;
   private char Sex;
   private String CNP;
    // Relația 1-la-M: Un Pacient are Multe Consultatii
    @OneToMany(mappedBy = "pacient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Programare> consultatii = new HashSet<>();
   @CreationTimestamp
   private LocalDateTime Data_nasterii;
   //@updateTmeStamp

}
