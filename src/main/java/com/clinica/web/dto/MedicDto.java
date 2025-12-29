package com.clinica.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class MedicDto {
    private int MedicID;
    private String Nume;
    private String Prenume;
    private String Specializare;
    private long Salariu;
    private char Sex;
    private String CNP;
    private LocalDateTime data_nasterii;
}
