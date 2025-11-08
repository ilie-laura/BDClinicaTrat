package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedicamentDto {
    private int MedicamentID;
    private String Nume;
    private int Stoc;
    private int Pret;
    private LocalDateTime Data_expirarii;
}
