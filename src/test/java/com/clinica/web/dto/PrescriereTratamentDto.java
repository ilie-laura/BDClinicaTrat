package com.clinica.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriereTratamentDto {
    private Long id;
    private String Durata;
}
