package com.clinica.web.dto;

import com.clinica.web.model.Programare;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class PacientDto {
    private int PacientID;
    private String Nume;
    private String Prenume;
    private String Oras;
    private String Strada;
    private String Localitate;
    private char Sex;
   // private String CNP;
    private LocalDateTime Data_nasterii;
    //@updateTmeStamp

}
