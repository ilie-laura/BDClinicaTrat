package com.clinica.web.repository;

import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.sql.Types.NULL;

@Repository
public class MedicRepository {

    private  final JdbcTemplate jdbcTemplate;

    private static final List<String> ALLOWED_FIELDS = List.of("Nume", "Prenume", "Specializare","Salariu","Sex");
    public MedicRepository( JdbcTemplate jdbcTemplate) {
this.jdbcTemplate = jdbcTemplate;
    }
    


    public Optional<Medic> findByMedicID(int medicID) {
        String sql = "SELECT * FROM Medic where MedicID = ?";
        List<Medic> medici = jdbcTemplate.query(sql, new Object[]{medicID}, (rs, rowNum) -> {
            Medic m = new Medic();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSpecializare(rs.getString("Specializare"));
            return m;
        });

        return medici.isEmpty() ? Optional.empty() : Optional.of(medici.get(0));
    }

    public List<Medic> search(String field, String value,Boolean dir) {
        // Verifica daca coloana este permisa
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        // Protecție la valoare null sau goală
        if (value == null || value.trim().isEmpty()) {
            return findAll(dir,field);
        }

        String sql;
        if (dir==null || dir==true)
            sql = "SELECT * FROM Medic WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" ASC";
        else
            sql="SELECT * FROM Medic WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" DESC";

        System.out.println("Executing search: field=" + field + ", value=" + value);

        return jdbcTemplate.query(sql,
                new Object[]{ value.trim() + "%" }, // pentru "începe cu"
                this::mapRow);
    }

    // Mapare ResultSet -> Pacient
    private Medic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Medic p= new Medic();
        p.setMedicID(rs.getInt("MedicID"));
        p.setNume(rs.getString("Nume"));
        p.setPrenume(rs.getString("Prenume"));
        p.setSpecializare(rs.getString("Specializare"));
        p.setSalariu(rs.getInt("Salariu"));
        p.setSex(rs.getString("Sex").charAt(0));


        return p;
    }
    public List<Medic> findAll(Boolean dir,String field) {
        String sql ;
        if(field!=null) {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Medic ORDER BY " + field + " ASC";
            else
                sql = "SELECT * FROM Medic ORDER BY " + field + " DESC";
        }
        else {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Medic ORDER BY Nume ASC";
            else
                sql = "SELECT * FROM Medic ORDER BY Nume DESC";
        }
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Medic m = new Medic();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSpecializare(rs.getString("Specializare"));
            m.setSalariu(rs.getLong(("Salariu")));
            m.setSex(rs.getString("Sex").charAt(0));
            return m;
        });
    }

    public Medic save(Medic p) {
        String sql = "INSERT INTO Medic (Nume, Prenume,Salariu, Specializare, Sex, CNP, Data_nasterii) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    p.getNume(),
                    p.getPrenume(),
                    p.getSalariu(),
                    p.getSpecializare(),
                    p.getSex() != 0 ? String.valueOf(p.getSex()) : null,
                    p.getCNP(),
                    p.getDataNasterii() != null
                            ? Date.valueOf(p.getDataNasterii())
                            : Date.valueOf(LocalDate.now())

            );
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("CNP deja existent!");
        }

        return p;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM Medic WHERE MedicID= ?",
                id
        );
    }

    public Medic findById(Long id) {
        String sql = "SELECT * FROM Medic WHERE MedicID = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRow, id);
    }

    public Medic update(Medic pacient) {
        jdbcTemplate.update(
                "UPDATE Medic SET nume=?, prenume=?, salariu=?, specializare=? WHERE MedicID=?",
                pacient.getNume(),
                pacient.getPrenume(),
                pacient.getSalariu(),
                pacient.getSpecializare(),
                pacient.getMedicID()
        );
        return pacient;
    }
    public Map<Integer, Integer> findNrProgramariPerMedic() {

        String sql = """
        SELECT m.MedicID, COUNT(pr.ProgramareID) AS nr_programari
        FROM Medic m
        LEFT JOIN Programare pr ON pr.MedicID = m.MedicID
        GROUP BY m.MedicID
    """;

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, Integer> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getInt("MedicID"), rs.getInt("nr_programari"));
            }
            return map;
        });
    }
    public Map<Integer, List<String>> findPacientiPerMedic() {

        String sql = """
        SELECT m.MedicID, p.Nume, p.Prenume
        FROM Medic m
        LEFT JOIN Programare pr ON pr.MedicID = m.MedicID
        LEFT JOIN Pacient p ON p.PacientID = pr.PacientID
        ORDER BY m.MedicID
    """;

        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, List<String>> map = new HashMap<>();

            while (rs.next()) {
                int medicId = rs.getInt("MedicID");
                String nume = rs.getString("Nume");
                String prenume = rs.getString("Prenume");

                map.computeIfAbsent(medicId, k -> new ArrayList<>());

                if (nume != null && prenume != null) {
                    map.get(medicId).add(nume + " " + prenume);
                }
            }
            return map;
        });
    }


}
