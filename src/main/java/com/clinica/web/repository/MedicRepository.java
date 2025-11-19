package com.clinica.web.repository;

import com.clinica.web.model.Medic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicRepository {

    private final JdbcTemplate jdbcTemplate;

    public MedicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Medic> findByMedicID(int medicID) {
        String sql = "SELECT * FROM Medic where MedicID = ?";
        List<Medic> medici = jdbcTemplate.query(sql, new Object[]{medicID}, (rs, rowNum) -> {
            Medic m = new Medic();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSpecializare(rs.getString("Specializare"));
            return m;
        });

        return medici.isEmpty() ? Optional.empty() : Optional.of(medici.get(0));
    }

    public List<Medic> findAll() {
        String sql = "SELECT * FROM Medic";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Medic m = new Medic();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSpecializare(rs.getString("Specializare"));
            return m;
        });
    }
}
