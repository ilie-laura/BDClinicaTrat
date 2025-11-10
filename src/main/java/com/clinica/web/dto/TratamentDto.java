package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TratamentDto {
    private int TratamentID;
    private String Nume;
    private LocalDateTime Data_inceput;
    private String Durata_tratament;
}
