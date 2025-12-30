package com.clinica.web.repository;

import com.clinica.web.model.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public class TratamentMedicatieRepository  {
  //  @Override
  private final JdbcTemplate jdbcTemplate;
    private static final List<String> ALLOWED_FIELDS = List.of("Doza", "Frecventa","doza","frecventa");
    public TratamentMedicatieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TratamentMedicatie> findAll(Boolean dir, String field) {
        String sql ;
        if(field!=null) {
            if (dir == null || dir == true)
                sql = "SELECT * FROM TratamentMedicatie ORDER BY " + field + " ASC";
            else
                sql = "SELECT * FROM TratamentMedicatie ORDER BY " + field + " DESC";
        }
        else {
            if (dir == null || dir == true)
                sql = "SELECT * FROM TratamentMedicatie ORDER BY TratamentID ASC";
            else
                sql = "SELECT * FROM TratamentMedicatieORDER BY TratamentID DESC";
        }
        return jdbcTemplate.query(sql, this::mapRow);
    }


    public List<TratamentMedicatie> search(String field, String value,Boolean dir) {

        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll(dir,field);
        }
        String sql;

        if (dir==null || dir==true)
            sql = "SELECT * FROM TratamentMedicatie WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" ASC";
        else
            sql="SELECT * FROM TratamentMedicatie WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" DESC";

        System.out.println("Executing search: field=" + field + ", value=" + value);

        return jdbcTemplate.query(sql,
                new Object[]{value.trim() + "%"},
                this::mapRow);
    }

    public TratamentMedicatie save(TratamentMedicatie p) {
        String sql = "INSERT INTO TratamentMedicatie (TratamentID, MedicamentID,Doza,Frecventa) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    p.getTratament().getTratamentId(),
                    p.getMedicament().getMedicamentId(),
                    p.getDoza(),
                    p.getFrecventa()
            );
        } catch (DuplicateKeyException e) {
            throw new IllegalStateException("Duplicate key exception: " + e.getMessage());
        }

        return p;
    }

    public void deleteById(Long id,Long id2) {
        jdbcTemplate.update(
                "DELETE FROM TratamentMedicatie WHERE TratamentID= ? and  MedicamentID= ?",
                id,id2
        );
    }

    public TratamentMedicatie findById(Long id,Long id2) {
        String sql = "SELECT * FROM TratamentMedicatie WHERE TratamentID = ? and MedicamentID=?";
        return jdbcTemplate.queryForObject(sql, this::mapRow, id,id2);
    }

    public TratamentMedicatie update(TratamentMedicatie  pacient) {
        jdbcTemplate.update(
                "UPDATE TratamentMedicatie SET doza=?, frecventa=? WHERE TratamentID=? AND MedicamentID=?",
                pacient.getDoza(),
                pacient.getFrecventa(),
                pacient.getTratament().getTratamentId(),
                pacient.getMedicament().getMedicamentId()
        );
        return pacient;
    }


    private TratamentMedicatie mapRow(ResultSet rs, int rowNum) throws SQLException {
        TratamentMedicatie tm = new TratamentMedicatie();

        Tratament tratament = new Tratament();
        tratament.setTratamentId(rs.getInt("TratamentID"));
        tm.setTratament(tratament);

        Medicament medicament = new Medicament();
        medicament.setMedicamentId(rs.getInt("MedicamentID"));
        tm.setMedicament(medicament);

        tm.setDoza(rs.getInt("Doza"));
        tm.setFrecventa(rs.getString("Frecventa"));

        return tm;
    }

}
