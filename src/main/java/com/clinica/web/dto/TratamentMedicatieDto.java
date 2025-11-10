package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TratamentMedicatieDto {
    int id;
    private int Doza;
    private String Frecventa;
}
