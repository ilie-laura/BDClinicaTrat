package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProgramareDto {
    private int ProgramareID;
    private int medicID;
    private int pacientID;
    private LocalDateTime Data_programare;
    private String Durata_programare;
}
