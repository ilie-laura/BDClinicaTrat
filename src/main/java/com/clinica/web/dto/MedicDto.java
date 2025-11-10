package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedicDto {
    private int MedicID;
    private String Nume;
    private String Prenume;
    private String Specializare;
    private long Salariu;
    private char Sex;
    private String CNP;
    private LocalDateTime Data_angajarii;
}
