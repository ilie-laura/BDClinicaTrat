package com.clinica.web.repository;

import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.model.PrescriereTratamentId;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

@Repository
public class PrescriereTratamentRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final List<String> ALLOWED_FIELDS = List.of("ProgramareID","TratamentID","Durata");
    public PrescriereTratamentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<PrescriereTratament> findAll() {
        String sql = "SELECT * FROM PrescriereTratament";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PrescriereTratament p = new PrescriereTratament();

            PrescriereTratamentId id = new PrescriereTratamentId();
            id.setProgramareID(rs.getLong("ProgramareID"));
            id.setTratamentID(rs.getLong("TratamentID"));

            p.setId(id);
            p.setDurata(rs.getString("Durata"));
            return p;
        });
    }
    public Optional<PrescriereTratament> findById(Long programareID, Long tratamentID) {
        String sql = "SELECT * FROM PrescriereTratament WHERE ProgramareID = ? AND TratamentID = ?";

        List<PrescriereTratament> results = jdbcTemplate.query(sql,
                new Object[]{programareID, tratamentID},
                (rs, rowNum) -> {
                    PrescriereTratament p = new PrescriereTratament();

                    PrescriereTratamentId id = new PrescriereTratamentId();
                    id.setProgramareID(rs.getLong("ProgramareID"));
                    id.setTratamentID(rs.getLong("TratamentID"));

                    p.setId(id);
                    p.setDurata(rs.getString("Durata"));

                    return p;
                });

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<PrescriereTratament> findByProgramareID(Long programareID) {
        String sql = "SELECT * FROM PrescriereTratament WHERE ProgramareID = ?";

        return jdbcTemplate.query(sql, new Object[]{programareID}, (rs, rowNum) -> {
            PrescriereTratament p = new PrescriereTratament();

            PrescriereTratamentId id = new PrescriereTratamentId();
            id.setProgramareID(rs.getLong("ProgramareID"));
            id.setTratamentID(rs.getLong("TratamentID"));

            p.setId(id);
            p.setDurata(rs.getString("Durata"));
            return p;
        });
    }

    public List<PrescriereTratament> findByTratamentID(Long tratamentID) {
        String sql = "SELECT * FROM PrescriereTratament WHERE TratamentID = ?";

        return jdbcTemplate.query(sql, new Object[]{tratamentID}, (rs, rowNum) -> {
            PrescriereTratament p = new PrescriereTratament();

            PrescriereTratamentId id = new PrescriereTratamentId();
            id.setProgramareID(rs.getLong("ProgramareID"));
            id.setTratamentID(rs.getLong("TratamentID"));

            p.setId(id);
            p.setDurata(rs.getString("Durata"));
            return p;
        });
    }
    private PrescriereTratament mapRow(ResultSet rs, int rowNum) throws SQLException {
        PrescriereTratament t = new PrescriereTratament();

        PrescriereTratamentId id = new PrescriereTratamentId();
        id.setProgramareID(rs.getLong("ProgramareID"));
        id.setTratamentID(rs.getLong("TratamentID"));

        t.setId(id);
        t.setDurata(rs.getString("Durata"));

        return t;
    }

    public List<PrescriereTratament> search(String field,String value) {
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

      String sql;
        if ("TratamentID".equals(field)) {
            sql = "SELECT pr.* FROM PrescriereTratament pr " +
                    "JOIN Tratament p ON pr.TratamentID = p.TratamentID " +
                    "WHERE p.Nume LIKE ? ORDER BY p.Nume " ;
        }
        else if ("ProgramareID".equals(field)) {
            sql = "SELECT pr.* FROM PrescriereTratament pr " +
                    "JOIN Programare m ON pr.ProgramareID = m.ProgramareID " +
                    "WHERE m.Data_programare LIKE ? ORDER BY m.Data_programare " ;
        }
        else {

            sql = "SELECT * FROM PrescriereTratament WHERE " + field + " LIKE ? ORDER BY " + field + " " ;
        }
        return jdbcTemplate.query(sql, new Object[]{"%" + value.trim() + "%"}, this::mapRow);
    }

    public  int savePrescriere(PrescriereTratament t) {
        String sql = "INSERT INTO PrescriereTratament (ProgramareID,TratamentID,Durata) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,
                t.getId().getProgramareID(),
                t.getId().getTratamentID(),
                t.getDurata());
    }


}
