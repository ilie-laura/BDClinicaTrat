package com.clinica.web.repository;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.dto.PacientDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RapoarteRepository {

    private final JdbcTemplate jdbcTemplate;

    public RapoarteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<MedicDto> mediciCuSalariuPesteMedie() {
        String sql = """
            SELECT * FROM Medic
            WHERE Salariu > (SELECT AVG(Salariu) FROM Medic)
        """;

        return jdbcTemplate.query(sql, (rs, i) -> {
            MedicDto m = new MedicDto();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSalariu(rs.getLong("Salariu"));
            return m;
        });
    }


    public List<PacientDto> pacientiCuProgramari() {
        String sql = """
            SELECT * FROM Pacient
            WHERE PacientID IN (
                SELECT DISTINCT pacientId FROM Programare
            )
        """;

        return jdbcTemplate.query(sql, (rs, i) -> {
            PacientDto p = new PacientDto();
            p.setPacientID(rs.getInt("PacientID"));
            p.setNume(rs.getString("Nume"));
            p.setPrenume(rs.getString("Prenume"));
            p.setOras(rs.getString("Oras"));
            return p;
        });
    }


    public List<MedicamentDto> medicamenteFolositeInTratamente() {
        String sql = """
            SELECT * FROM Medicament
            WHERE medicamentID IN (
                SELECT medicamentID FROM TratamentMedicatie
            )
        """;

        return jdbcTemplate.query(sql, (rs, i) -> {
            MedicamentDto m = new MedicamentDto();
            m.setMedicamentID(rs.getInt("medicamentID"));
            m.setNume(rs.getString("nume"));
            m.setStoc(rs.getInt("stoc"));
            m.setPret(rs.getInt("pret"));
            return m;
        });
    }


    public List<MedicDto> mediciFaraProgramari() {
        String sql = """
            SELECT * FROM Medic
            WHERE MedicID NOT IN (
                SELECT DISTINCT medicId FROM Programare
            )
        """;

        return jdbcTemplate.query(sql, (rs, i) -> {
            MedicDto m = new MedicDto();
            m.setMedicID(rs.getInt("MedicID"));
            m.setNume(rs.getString("Nume"));
            m.setPrenume(rs.getString("Prenume"));
            m.setSpecializare(rs.getString("Specializare"));
            return m;
        });
    }
}
