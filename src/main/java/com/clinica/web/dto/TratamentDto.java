package com.clinica.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentDto {
    private int TratamentID;
    private String Nume;
    private LocalDateTime Data_inceput;
    private String Durata_tratament;
}
