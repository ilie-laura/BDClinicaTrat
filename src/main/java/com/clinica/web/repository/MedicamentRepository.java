package com.clinica.web.repository;

import com.clinica.web.model.Medicament;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicamentRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicamentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ------------------------------
    // Find Medicament by ID
    // ------------------------------
    public Optional<Medicament> findByMedicamentID(int medicamentID) {
        String sql = "SELECT * FROM Medicament WHERE MedicamentID = ?";

        List<Medicament> medicamente = jdbcTemplate.query(sql, new Object[]{medicamentID}, (rs, rowNum) -> {
            Medicament m = new Medicament();
            m.setMedicamentID(rs.getInt("MedicamentID"));
            m.setNume(rs.getString("Nume"));
            m.setStoc(rs.getInt("Stoc"));
            m.setPret(rs.getInt("Pret"));

            Timestamp ts = rs.getTimestamp("Data_expirare");
            if (ts != null) {
                m.setData_expirarii(ts.toLocalDateTime());
            }

            return m;
        });

        return medicamente.isEmpty() ? Optional.empty() : Optional.of(medicamente.get(0));
    }

    // ------------------------------
    // Find all Medicamente
    // ------------------------------
    public List<Medicament> findAll() {
        String sql = "SELECT * FROM Medicament";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Medicament m = new Medicament();
            m.setMedicamentID(rs.getInt("MedicamentID"));
            m.setNume(rs.getString("Nume"));
            m.setStoc(rs.getInt("Stoc"));
            m.setPret(rs.getInt("Pret"));

            Timestamp ts = rs.getTimestamp("Data_expirare");
            if (ts != null) {
                m.setData_expirarii(ts.toLocalDateTime());
            }

            return m;
        });
    }
}
