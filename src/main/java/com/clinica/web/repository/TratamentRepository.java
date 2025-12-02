package com.clinica.web.repository;

import com.clinica.web.model.Tratament;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class TratamentRepository {

    private final JdbcTemplate jdbcTemplate;

    // câmpurile permise pentru search
    private static final List<String> ALLOWED_FIELDS = List.of("Nume", "Data_inceput", "Durata_tratament");

    public TratamentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // mapare ResultSet → Tratament
    private Tratament mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tratament t = new Tratament();
        t.setTratamentID(rs.getInt("TratamentID"));
        t.setNume(rs.getString("Nume"));
        t.setDurata_tratament(rs.getString("Durata_tratament"));

        Timestamp ts = rs.getTimestamp("Data_inceput");
        if (ts != null) {
            t.setData_inceput(ts.toLocalDateTime());
        }

        return t;
    }

    // -------------------------------
    // Find by ID
    // -------------------------------
    public Optional<Tratament> findByTratamentID(int tratamentID) {
        String sql = "SELECT * FROM Tratament WHERE TratamentID = ?";
        List<Tratament> list = jdbcTemplate.query(sql, new Object[]{tratamentID}, this::mapRow);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    // -------------------------------
    // Find all
    // -------------------------------
    public List<Tratament> findAll() {
        String sql = "SELECT * FROM Tratament";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    // -------------------------------
    // Search după câmp permis
    // -------------------------------
    public List<Tratament> search(String field,String value) {
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM Tratament WHERE RTRIM(" + field + ") LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{value.trim() + "%"}, this::mapRow);
    }

    // -------------------------------
    // Save (insert)
    // -------------------------------
    public int saveTratament(Tratament t) {
        String sql = "INSERT INTO Tratament (Nume, Durata_tratament, Data_inceput) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                t.getNume(),
                t.getDurata_tratament(),
                Timestamp.valueOf(t.getData_inceput()));
    }

    // -------------------------------
    // Delete
    // -------------------------------
    public int delete(int id) {
        String sql = "DELETE FROM Tratament WHERE TratamentID = ?";
        return jdbcTemplate.update(sql, id);
    }
}
