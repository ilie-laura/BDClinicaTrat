package com.clinica.web.repository;

import com.clinica.web.model.Medic;
import com.clinica.web.model.Pacient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;

@Repository
public class MedicRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final List<String> ALLOWED_FIELDS = List.of("Nume", "Prenume", "Specializare","Salariu","Sex");
    public MedicRepository(JdbcTemplate jdbcTemplate) {
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

    public List<Medic> search(String field, String value) {
        // Verifica daca coloana este permisa
        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        // Protecție la valoare null sau goală
        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM Medic WHERE RTRIM(" + field + ") LIKE ?";

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
    public List<Medic> findAll() {
        String sql = "SELECT * FROM Medic";
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
                            ? java.sql.Date.valueOf(p.getDataNasterii())
                            : java.sql.Date.valueOf(LocalDate.now())

            );
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("CNP deja existent!");
        }

        return p;
    }
}
