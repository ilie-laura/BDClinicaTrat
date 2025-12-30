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
        t.setTratamentId(rs.getInt("TratamentID"));
        t.setNume(rs.getString("Nume"));
        t.setDurata_tratament(rs.getString("Durata_tratament"));

        Timestamp ts = rs.getTimestamp("Data_inceput");
        if (ts != null) {
            t.setData_inceput(ts.toLocalDateTime());
        }

        return t;
    }


    public Optional<Tratament> findByTratamentID(int tratamentID) {
        String sql = "SELECT * FROM Tratament WHERE TratamentID = ?";
        List<Tratament> list = jdbcTemplate.query(sql, new Object[]{tratamentID}, this::mapRow);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }


    public List<Tratament> findAll(Boolean dir,String field) {
        String sql;
        if(field!=null) {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Tratament ORDER BY " + field + " ASC";
            else
                sql = "SELECT * FROM Tratament ORDER BY " + field + " DESC";
        }
        else {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Tratament ORDER BY Nume ASC";
            else
                sql = "SELECT * FROM Tratament ORDER BY Nume DESC";
        }
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public List<Tratament> search(String field,String value,Boolean dir) {
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll(dir,field);
        }

        String sql;
        if (dir==null || dir==true)
            sql = "SELECT * FROM Tratament WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" ASC";
        else
            sql="SELECT * FROM Tratament WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" DESC";
        return jdbcTemplate.query(sql, new Object[]
                {value.trim() + "%"
                }, this::mapRow);
    }


    public int saveTratament(Tratament t) {
        String sql = "INSERT INTO Tratament (Nume, Durata_tratament, Data_inceput) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                t.getNume(),
                t.getDurata_tratament(),
                Timestamp.valueOf(t.getData_inceput()));
    }


    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM Tratament WHERE TratamentID= ?",
                id
        );
    }

    public Tratament findById(Long id) {
        String sql = "SELECT * FROM Tratament WHERE TratamentID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRow, id);
    }

    public Tratament update(Tratament pacient) {
        jdbcTemplate.update(
                "UPDATE Tratament SET nume=?, data_inceput=?,durata_tratament=? WHERE TratamentID=?",
                pacient.getNume(),
                pacient.getData_inceput(),
                pacient.getDurata_tratament(),
                pacient.getTratamentId()
        );
        return pacient;
    }

}
