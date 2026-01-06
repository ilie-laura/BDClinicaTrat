package com.clinica.web.repository;

import com.clinica.web.dto.MedicDto;
import com.clinica.web.dto.MedicamentDto;
import com.clinica.web.dto.PacientDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RapoarteRepository {

    private static JdbcTemplate jdbcTemplate = null;

    public RapoarteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static List<Map<String, Object>> mediciActiviAzi() {

        String sql = """
        SELECT 
            m.MedicID,
            m.Nume,
            m.Prenume,
            m.Specializare,
            COUNT(p.ProgramareID) AS nrProgramari
        FROM Medic m
        JOIN Programare p ON m.MedicID = p.MedicID
        WHERE CAST(p.Data_programare AS DATE) = CAST(GETDATE() AS DATE)
        GROUP BY m.MedicID, m.Nume, m.Prenume, m.Specializare
        ORDER BY nrProgramari DESC
    """;

        return jdbcTemplate.queryForList(sql);
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


    public List<Map<String, Object>> salariiMediciCuProgramari(long minSalariu) {
        String sql = """
        SELECT 
            m.MedicID,
            m.Nume,
            m.Prenume,
            m.Salariu,
            COUNT(p.ProgramareID) AS nrProgramari
        FROM Medic m
        LEFT JOIN Programare p ON m.MedicID = p.MedicID
        WHERE m.Salariu >= ?
        GROUP BY m.MedicID, m.Nume, m.Prenume, m.Salariu
        ORDER BY m.Salariu DESC
    """;

        return jdbcTemplate.queryForList(sql, minSalariu);
    }public List<Map<String, Object>> pacientiCuCheltuieliPesteMedie(Double pragMinim) {
        // Dacă nu vine niciun prag din HTML, punem 0 default
        double prag = (pragMinim != null) ? pragMinim : 0.0;

        String sql = """
        SELECT p.Nume, p.Prenume, SUM(m.Pret) as TotalCheltuit
        FROM Pacient p
        JOIN Programare pr ON p.PacientID = pr.PacientID
        JOIN PrescriereTratament pt ON pr.ProgramareID = pt.ProgramareID
        JOIN TratamentMedicatie tm ON pt.TratamentID = tm.TratamentID
        JOIN Medicament m ON tm.MedicamentID = m.MedicamentID
        GROUP BY p.PacientID, p.Nume, p.Prenume
        HAVING SUM(m.Pret) > (
            SELECT AVG(TotalPePacient) FROM (
                SELECT SUM(med.Pret) as TotalPePacient
                FROM PrescriereTratament pt2
                JOIN TratamentMedicatie tm2 ON pt2.TratamentID = tm2.TratamentID
                JOIN Medicament med ON tm2.MedicamentID = med.MedicamentID
                GROUP BY pt2.ProgramareID
            ) as Subtabel
        ) AND SUM(m.Pret) >= ? 
        ORDER BY TotalCheltuit DESC
    """;
        return jdbcTemplate.queryForList(sql, prag);
    }
    public List<Map<String, Object>> venituriMedicamente() {
        String sql = """
        SELECT 
            m.Nume, 
            COUNT(tm.TratamentID) AS NrPrescrieri,
            SUM(m.Pret) AS VenitGenerat
        FROM Medicament m
        JOIN TratamentMedicatie tm ON m.MedicamentID = tm.MedicamentID
        GROUP BY m.MedicamentID, m.Nume
        ORDER BY VenitGenerat DESC
    """;
        return jdbcTemplate.queryForList(sql);
    }

    public Double getTotalVenit() {
        return jdbcTemplate.queryForObject("SELECT SUM(Pret) FROM Medicament m JOIN TratamentMedicatie tm ON m.MedicamentID = tm.MedicamentID", Double.class);
    }

    public Integer getNrProgramariNoi() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Programare WHERE CAST(Data_programare AS DATE) = CAST(GETDATE() AS DATE)", Integer.class);
    }
    public Integer getNrMedicamenteStocCritic(int prag) {
        // Punem pragul direct sau prin parametru ?
        String sql = "SELECT COUNT(*) FROM Medicament WHERE Stoc <= ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, prag);
        return (count != null) ? count : 0;
    }
}
