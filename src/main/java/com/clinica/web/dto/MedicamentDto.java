package com.clinica.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicamentDto {
    private int medicamentID;
    private String nume;
    private int stoc;
    private int pret;
    private LocalDate data_expirarii;

}
