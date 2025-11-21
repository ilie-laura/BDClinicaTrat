package com.clinica.web.repository;

import com.clinica.web.model.PrescriereTratament;
import com.clinica.web.model.PrescriereTratamentId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PrescriereTratamentRepository {

    private final JdbcTemplate jdbcTemplate;

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

    //-----------------------------------------------------
    // FIND BY TratamentID
    //-----------------------------------------------------
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
}
