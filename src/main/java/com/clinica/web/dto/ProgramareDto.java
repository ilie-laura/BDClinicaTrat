package com.clinica.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgramareDto {
    private int programareId;
    private int medicId;
    private int pacientId;
    private LocalDateTime dataProgramare;
    private String durataProgramare;

//    public int getProgramareID() {
//        return ProgramareID;
//    }
//    public int getMedicID() {
//        return medicID;
//    }
//    public int getPacientID() {
//        return pacientID;
//    }
}
