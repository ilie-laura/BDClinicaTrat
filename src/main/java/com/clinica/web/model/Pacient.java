package com.clinica.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
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
    private int pacientID;
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
   private LocalDate Data_nasterii;

    // GETTERE
    public int getPacientID() {
        return pacientID;
    }

    public String getNume() {
        return Nume;
    }

    public String getPrenume() {
        return Prenume;
    }

    public LocalDate getDataNasterii() {
        return Data_nasterii;
    }

    public String getCnp() {
        return CNP;
    }

    public Character getSex() {
        return Sex;
    }

    public String getStrada() {
        return Strada;
    }

    public String getOras() {
        return Oras;
    }

    public String getLocalitate() {
        return Localitate;
    }

    // SETTERE
    public void setPacientID(int pacientID) {
        this.pacientID = pacientID;
    }

    public void setNume(String nume) {
        this.Nume = nume;
    }

    public void setPrenume(String prenume) {
        this.Prenume = prenume;
    }

    public void setDataNasterii(LocalDate dataNasterii) {
        this.Data_nasterii = dataNasterii;
    }

    public void setCnp(String cnp) {
        this.CNP = cnp;
    }

    public void setSex(Character sex) {
        this.Sex = sex;
    }

    public void setStrada(String strada) {
        this.Strada = strada;
    }

    public void setOras(String oras) {
        this.Oras = oras;
    }

    public void setLocalitate(String localitate) {
        this.Localitate = localitate;
    }
    //@updateTmeStamp

}
