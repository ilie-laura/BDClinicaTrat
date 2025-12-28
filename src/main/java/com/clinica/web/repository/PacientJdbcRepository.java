package com.clinica.web.repository;

import com.clinica.web.model.Pacient;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PacientJdbcRepository {

    // Coloanele permise pentru cautare
    private static final List<String> ALLOWED_FIELDS = List.of("Nume", "Prenume", "Oras", "Localitate", "Sex");

    private final JdbcTemplate jdbcTemplate;

    public PacientJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Pacient> findAll(Boolean dir,String field) {
        String sql ;
        if(field!=null) {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Pacient ORDER BY " + field + " ASC";
            else
                sql = "SELECT * FROM Pacient ORDER BY " + field + " DESC";
        }
        else {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Pacient ORDER BY Nume ASC";
            else
                sql = "SELECT * FROM Pacient ORDER BY Nume DESC";
        }
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public List<Pacient> search(String field, String value,Boolean dir) {
        // Verifica daca coloana este permisa
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll(dir,field);
        }
        String sql;

        if (dir==null || dir==true)
         sql = "SELECT * FROM Pacient WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" ASC";
        else
        sql="SELECT * FROM Pacient WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" DESC";

        System.out.println("Executing search: field=" + field + ", value=" + value);

        return jdbcTemplate.query(sql,
                new Object[]{value.trim() + "%"},
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
            p.setDataNasterii(LocalDate.from(rs.getDate("Data_nasterii").toLocalDate().atStartOfDay()));
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
                    p.getDataNasterii() != null ? java.sql.Timestamp.valueOf(p.getDataNasterii().atStartOfDay()) : java.sql.Timestamp.valueOf(LocalDateTime.now())
            );
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("CNP deja existent!");
        }

        return p;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM Pacient WHERE PacientID= ?",
                id
        );
    }

    public Pacient findById(Long id) {
        String sql = "SELECT * FROM Pacient WHERE PacientID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRow, id);
    }

    public Pacient update(Pacient pacient) {
        jdbcTemplate.update(
                "UPDATE pacient SET nume=?, prenume=?, oras=?, localitate=?, strada=?WHERE PacientID=?",
                pacient.getNume(),
                pacient.getPrenume(),
                pacient.getOras(),
                pacient.getLocalitate(),
                pacient.getStrada(),
                pacient.getPacientID()
        );
        return pacient;
    }


}
