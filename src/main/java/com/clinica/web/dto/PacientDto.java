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
