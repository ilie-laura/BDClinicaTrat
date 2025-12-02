package com.clinica.web.repository;

import com.clinica.web.model.Programare;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ProgramareRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> ALLOWED_FIELDS =
            List.of("PacientID", "pacientID", "MedicID", "medicID",
                    "Data_programare", "data_programare",
                    "Durata_programare", "durata_programare");


    public ProgramareRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Programare> search(String field, String value) {

        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM Programare WHERE CONVERT(VARCHAR(19), " + field + ", 120) LIKE ?";


        return jdbcTemplate.query(
                sql,
                new Object[]{value.trim() + "%"},
                this::mapRow
        );
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
    private Programare mapRow(ResultSet rs, int rowNum) throws SQLException {
        Programare p = new Programare();

        p.setProgramareID(rs.getInt("ProgramareID"));
        p.setPacientID(rs.getInt("PacientID"));
        p.setMedicID(rs.getInt("MedicID"));

        Timestamp ts = rs.getTimestamp("Data_programare");
        if (ts != null) {
            p.setDataProgramarii(ts.toLocalDateTime());
        }

        p.setDurataProgramare(rs.getString("Durata_programare"));

        return p;
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

    public Programare save(Programare p) {
        String sql = "INSERT INTO Programare (PacientID, MedicID, Data_programare, Durata_programare) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                p.getPacient() != null ? p.getPacient().getPacientID() : p.getPacientID(),
                p.getMedic() != null ? p.getMedic().getMedicID() : p.getMedicID(),
                p.getDataProgramarii(),
                p.getDurata_programare()
        );

        return p;
    }
}
