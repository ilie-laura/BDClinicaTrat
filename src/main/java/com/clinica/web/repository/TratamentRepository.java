package com.clinica.web.repository;

import com.clinica.web.model.Tratament;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TratamentRepository {

    private final JdbcTemplate jdbcTemplate;

    public TratamentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //-----------------------------------------------------
    // FIND BY ID
    //-----------------------------------------------------
    public Optional<Tratament> findByTratamentID(int tratamentID) {
        String sql = "SELECT * FROM Tratament WHERE TratamentID = ?";

        List<Tratament> rezultate = jdbcTemplate.query(sql, new Object[]{tratamentID}, (rs, rowNum) -> {
            Tratament t = new Tratament();
            t.setTratamentID(rs.getInt("TratamentID"));
            t.setNume(rs.getString("Nume"));
            t.setDurata_tratament(rs.getString("Durata_tratament"));

            Timestamp ts = rs.getTimestamp("Data_inceput");
            if (ts != null) {
                t.setData_inceput(ts.toLocalDateTime());
            }

            return t;
        });

        return rezultate.isEmpty() ? Optional.empty() : Optional.of(rezultate.get(0));
    }

    //-----------------------------------------------------
    // FIND ALL
    //-----------------------------------------------------
    public List<Tratament> findAll() {
        String sql = "SELECT * FROM Tratament";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tratament t = new Tratament();
            t.setTratamentID(rs.getInt("TratamentID"));
            t.setNume(rs.getString("Nume"));
            t.setDurata_tratament(rs.getString("Durata_tratament"));

            Timestamp ts = rs.getTimestamp("Data_inceput");
            if (ts != null) {
                t.setData_inceput(ts.toLocalDateTime());
            }

            return t;
        });
    }
}
