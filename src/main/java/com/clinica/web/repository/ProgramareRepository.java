package com.clinica.web.repository;

import com.clinica.web.model.Programare;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProgramareRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProgramareRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //-----------------------------------------------------
    // FIND BY ID
    //-----------------------------------------------------
    public Optional<Programare> findByProgramareID(Long programareID) {
        String sql = "SELECT * FROM Programare WHERE ProgramareID = ?";

        List<Programare> programari = jdbcTemplate.query(sql, new Object[]{programareID}, (rs, rowNum) -> {
            Programare p = new Programare();
            p.setProgramareID(rs.getInt("ProgramareID"));
            p.setPacientID(rs.getInt("PacientID"));
            p.setMedicID(rs.getInt("MedicID"));
            p.setDataProgramarii(rs.getTimestamp("DataProgramarii").toLocalDateTime());
            return p;
        });

        return programari.isEmpty() ? Optional.empty() : Optional.of(programari.get(0));
    }

    //-----------------------------------------------------
    // FIND ALL
    //-----------------------------------------------------
    public List<Programare> findAll() {
        String sql = "SELECT * FROM Programare";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Programare p = new Programare();
            p.setProgramareID(rs.getInt("ProgramareID"));
            p.setPacientID(rs.getInt("PacientID"));
            p.setMedicID(rs.getInt("MedicID"));
            p.setDataProgramarii(rs.getTimestamp("Data_programare").toLocalDateTime());
            p.setDurataProgramare(rs.getString("Durata_programare"));

            return p;
        });
    }
}
