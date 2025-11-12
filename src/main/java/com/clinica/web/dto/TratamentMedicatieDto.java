package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TratamentMedicatieDto {
    private long tratamentID;
    private long medicamentID;
    private int Doza;
    private String Frecventa;
}
