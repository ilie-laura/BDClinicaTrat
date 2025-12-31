package com.clinica.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriereTratamentDto {
   // private Long id;
    private long programareID;
    private long tratamentID;
    private String durata;

    private String programareInfo;
    private String tratamentNume;

}
