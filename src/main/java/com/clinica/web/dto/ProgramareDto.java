package com.clinica.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramareDto {
    private int ProgramareID;
    private int medicID;
    private int pacientID;
    private LocalDateTime Data_programare;
    private String Durata_programare;
}
