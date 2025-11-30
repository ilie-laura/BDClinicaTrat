package com.clinica.web.repository;

import com.clinica.web.model.Medicament;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicamentRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> ALLOWED_FIELDS =
            List.of("Nume", "Data_expirare", "Stoc", "Pret");

    public MedicamentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ---------------------------------------------------------
    // Find by ID
    // ---------------------------------------------------------
    public Optional<Medicament> findByMedicamentID(int medicamentID) {
        String sql = "SELECT * FROM Medicament WHERE MedicamentID = ?";

        List<Medicament> list = jdbcTemplate.query(
                sql,
                new Object[]{medicamentID},
                this::mapRow
        );

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    // ---------------------------------------------------------
    // Search with allowed fields
    // ---------------------------------------------------------
    public List<Medicament> search(String field, String value) {

        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll();
        }

        String sql = "SELECT * FROM Medicament WHERE RTRIM(" + field + ") LIKE ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{ value.trim() + "%" },
                this::mapRow
        );
    }

    // ---------------------------------------------------------
    // Map ResultSet → Medicament
    // ---------------------------------------------------------
    private Medicament mapRow(ResultSet rs, int rowNum) throws SQLException {
        Medicament m = new Medicament();
        m.setMedicamentID(rs.getInt("MedicamentID"));
        m.setNume(rs.getString("Nume"));
        m.setStoc(rs.getInt("Stoc"));
        m.setPret(rs.getInt("Pret"));


        LocalDate date = rs.getObject("Data_expirare", LocalDate.class);
        m.setData_expirarii(date);


        return m;
    }

    public List<Medicament> findAll() {
        String sql = "SELECT * FROM Medicament";
        return jdbcTemplate.query(sql, this::mapRow);
    }
    public Medicament save(Medicament medicament) {
        String sql = "INSERT INTO Medicament (Nume, Data_expirare, Stoc, Pret) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                medicament.getNume(),
                medicament.getData_expirarii(),
                medicament.getStoc(),
                medicament.getPret()
        );

        return medicament;
    }

}
