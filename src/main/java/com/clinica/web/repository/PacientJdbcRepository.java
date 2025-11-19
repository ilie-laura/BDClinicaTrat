package com.clinica.web.repository;

import com.clinica.web.model.Pacient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacientJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public PacientJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pacient> findAllPacients() {

        String sql = "SELECT * FROM Pacient";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pacient p = new Pacient();

            p.setPacientID(rs.getInt("PacientID"));
            p.setNume(rs.getString("Nume"));
            p.setPrenume(rs.getString("Prenume"));
            p.setOras(rs.getString("Oras"));
            p.setLocalitate(rs.getString("Localitate"));
            p.setStrada(rs.getString("Strada"));
            return p;
        });
    }
}
