package com.clinica.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PrescriereTratamentId implements Serializable {

    @Column(name = "TratamentID")
    private Long tratamentID;
    @Column(name = "ProgramareID")
    private Long programareID;

    public PrescriereTratamentId() {}

    public PrescriereTratamentId(Long tratamentID, Long programareID) {
        this.tratamentID = tratamentID;
        this.programareID = programareID;
    }

    public Long getTratamentID() {
        return tratamentID;
    }

    public void setTratamentID(Long tratamentID) {
        this.tratamentID = tratamentID;
    }

    public Long getProgramareID() {
        return programareID;
    }

    public void setProgramareID(Long programareID) {
        this.programareID = programareID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrescriereTratamentId that)) return false;
        return Objects.equals(tratamentID, that.tratamentID) &&
                Objects.equals(programareID, that.programareID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tratamentID, programareID);
    }
}
