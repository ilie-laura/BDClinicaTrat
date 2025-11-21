package com.clinica.web.repository;

import com.clinica.web.model.Pacient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PacientJdbcRepository {

    // Coloanele permise pentru cautare
    private static final List<String> ALLOWED_FIELDS = List.of("Nume", "Prenume", "Oras","Localitate","Sex");

    private final JdbcTemplate jdbcTemplate;

    public PacientJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pacient> findAll() {
        String sql = "SELECT * FROM Pacient";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public List<Pacient> search(String field, String value) {
        // Verifica daca coloana este permisa
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        // Protecție la valoare null sau goală
        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM Pacient WHERE RTRIM(" + field + ") LIKE ?";

        System.out.println("Executing search: field=" + field + ", value=" + value);

        return jdbcTemplate.query(sql,
                new Object[]{ value.trim() + "%" }, // pentru "începe cu"
                this::mapRow);
    }

    // Mapare ResultSet -> Pacient
    private Pacient mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pacient p = new Pacient();
        p.setPacientID(rs.getInt("PacientID"));
        p.setNume(rs.getString("Nume"));
        p.setPrenume(rs.getString("Prenume"));
        p.setOras(rs.getString("Oras"));
        p.setLocalitate(rs.getString("Localitate"));
        p.setStrada(rs.getString("Strada"));
        p.setCnp(rs.getString("CNP"));

        if (rs.getDate("Data_nasterii") != null) {
            p.setDataNasterii(rs.getDate("Data_nasterii").toLocalDate().atStartOfDay());
        }

        return p;
    }
    public Pacient save(Pacient p) {
        String sql = "INSERT INTO Pacient (Nume, Prenume, Oras, Strada, Localitate, Sex, CNP, Data_nasterii) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    p.getNume(),
                    p.getPrenume(),
                    p.getOras(),
                    p.getStrada(),
                    p.getLocalitate(),
                    p.getSex() != null ? p.getSex().toString() : null,
                    p.getCnp(),
                    p.getDataNasterii() != null ? java.sql.Timestamp.valueOf(p.getDataNasterii()) : java.sql.Timestamp.valueOf(LocalDateTime.now())
            );
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("CNP deja existent!");
        }

        return p;
    }

}
