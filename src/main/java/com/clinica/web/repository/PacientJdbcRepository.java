package com.clinica.web.repository;

import com.clinica.web.model.Pacient;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Integer, LocalDateTime> findUltimaProgramarePerPacient() {

        String sql = """
        SELECT PacientID, MAX(data_Programare) AS ultima_programare
        FROM Programare
        GROUP BY PacientID
    """;

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, LocalDateTime> result = new HashMap<>();
            while (rs.next()) {
                Integer pacientId = rs.getInt("PacientID");
                Timestamp ts = rs.getTimestamp("ultima_programare");
                if (ts != null) {
                    result.put(pacientId, ts.toLocalDateTime());
                }
            }
            return result;
        });
    }
    public void creeazaProgramareDupaCNP(
            String cnp,
            int medicId,
            LocalDate data,
            int durata,
            String motiv
    ) {

        String sql = """
            INSERT INTO Programare (PacientID, MedicID, Data_Programare, Durata_programare)
            SELECT p.PacientID, ?, ?, ?
            FROM Pacient p
            WHERE p.CNP = ?
        """;

        int rows = jdbcTemplate.update(
                sql,
                medicId,
                Timestamp.valueOf(data.atStartOfDay()),
                durata,
                motiv,
                cnp
        );

        if (rows == 0) {
            throw new IllegalArgumentException("Pacientul cu acest CNP nu există");
        }
    }
    public boolean existsByCnp(String cnp) {

        String sql = "SELECT COUNT(*) FROM Pacient WHERE CAST(CNP AS VARCHAR) = ?";

        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cnp.trim());
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public Map<Integer, Map<String, Object>> findPacientExtraStats() {
        // Subcerere 1  Numărul total de programări
        // Subcerere 2 Diferența de zile de la prima până la ultima vizită
        String sql = """
        SELECT p.PacientID, 
               stats.total_programari,
               stats.zile_frecventa
        FROM Pacient p
        LEFT JOIN (
            SELECT PacientID, 
                   COUNT(*) AS total_programari,
                   DATEDIFF(day, MIN(Data_Programare), MAX(Data_Programare)) AS zile_frecventa
            FROM Programare 
            GROUP BY PacientID
        ) AS stats ON p.PacientID = stats.PacientID
    """;

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, Map<String, Object>> statsMap = new HashMap<>();
            while (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("total", rs.getInt("total_programari"));
                data.put("frecventa", rs.getInt("zile_frecventa"));
                statsMap.put(rs.getInt("PacientID"), data);
            }
            return statsMap;
        });
    }

}
