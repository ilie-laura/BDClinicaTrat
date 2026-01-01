package com.clinica.web.repository;

import com.clinica.web.dto.ProgramareDto;
import com.clinica.web.model.Programare;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ProgramareRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> ALLOWED_FIELDS =
            List.of("PacientID", "pacientID","pacientId", "MedicID", "medicID",
                    "Data_programare", "data_programare",
                    "Durata_programare", "durata_programare");


    public ProgramareRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Programare> search(String field, String value, Boolean dir) {

        if (!ALLOWED_FIELDS.contains(field)) {
            throw new IllegalArgumentException("Invalid column for filtering: " + field);
        }

        if (value == null || value.trim().isEmpty()) {
            return findAll(dir,field);
        }

        String sql;
        if (dir==null || dir==true)
            sql = "SELECT * FROM Programare WHERE RTRIM(" + field + ") LIKE ? ORDER BY "+field+" ASC";
        else
            sql="SELECT * FROM Programare WHERE RTRIM(" + field + ") LIKE ? ORDER BY " +field+ " DESC";

        return jdbcTemplate.query(
                sql,
                new Object[]{value.trim() + "%"},
                this::mapRow
        );
    }

    public Programare findByID(Long programareID) {
        String sql = "SELECT * FROM Programare WHERE ProgramareID = ?";

        List<Programare> programari = jdbcTemplate.query(sql, new Object[]{programareID}, (rs, rowNum) -> {
            Programare p = new Programare();
            p.setProgramareID(rs.getInt("ProgramareID"));
            p.setPacientID(rs.getInt("PacientID"));
            p.setMedicID(rs.getInt("MedicID"));
            p.setDurata_programare("Durata_programare");
            p.setDataProgramarii(rs.getTimestamp("Data_programare").toLocalDateTime());
            return p;
        });
        return jdbcTemplate.queryForObject(sql, this::mapRow, programareID);
    }
    private Programare mapRow(ResultSet rs, int rowNum) throws SQLException {
        Programare p = new Programare();

        p.setProgramareID(rs.getInt("ProgramareID"));
        p.setPacientID(rs.getInt("PacientID"));
        p.setMedicID(rs.getInt("MedicID"));

        Timestamp ts = rs.getTimestamp("Data_programare");
        if (ts != null) {
            p.setDataProgramarii(ts.toLocalDateTime());
        }

        p.setDurataProgramare(rs.getString("Durata_programare"));

        return p;
    }

    public List<Programare> findAll(Boolean dir,String field) {
        String sql;
        if(field!=null) {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Programare ORDER BY " + field + " ASC";
            else
                sql = "SELECT * FROM Programare ORDER BY " + field + " DESC";
        }
        else {
            if (dir == null || dir == true)
                sql = "SELECT * FROM Programare ORDER BY Data_programare ASC";
            else
                sql = "SELECT * FROM Programare ORDER BY Data_programare DESC";
        }
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Programare p = new Programare();
            p.setProgramareID(rs.getInt("ProgramareID"));
            p.setPacientID(rs.getInt("PacientId"));
            p.setMedicID(rs.getInt("MedicID"));
            p.setData_programare(rs.getTimestamp("Data_programare").toLocalDateTime());
            p.setDurataProgramare(rs.getString("Durata_programare"));

            return p;
        });
    }

    public Programare save(Programare p) {
        String sql = "INSERT INTO Programare (PacientID, MedicID, Data_programare, Durata_programare) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                p.getPacient() != null ? p.getPacient().getPacientID() : p.getPacientID(),
                p.getMedic() != null ? p.getMedic().getMedicID() : p.getMedicID(),
                p.getDataProgramarii(),
                p.getDurata_programare()
        );

        return p;
    }


    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM Programare WHERE ProgramareID= ?",
                id
        );
    }

    public Programare update(Programare pacient) {
        jdbcTemplate.update(
                "UPDATE programare SET Data_programare=?, Durata_programare=?, MedicID=?, PacientID=? WHERE ProgramareID=?",
                pacient.getData_programare(),
                pacient.getDurata_programare(),
                pacient.getMedic().getMedicID(),
                pacient.getPacientID(),
                pacient.getProgramareID()
        );
        return pacient;
    }

    public void creeazaProgramareDupaCNP(String cnp, int medicID, LocalDate appointmentDate, int durata, String motiv) {
        {

            String sql = """
            INSERT INTO Programare
                (PacientId, MedicID, Data_Programare, Durata_programare)
            SELECT
                p.PacientId,
                ?, ?, ?
            FROM Pacient p
            WHERE p.CNP = ?
        """;

            int rows = jdbcTemplate.update(
                    sql,
                    medicID,
                    Timestamp.valueOf(appointmentDate.atStartOfDay()), // ?
                    durata ,                                  // ?
                    cnp
            )
                    ;
    }
}
    public List<Programare> findWithDurataAboveAverage() {

        String sql = """ 
        SELECT *
        FROM Programare
        WHERE
            CASE
                WHEN Durata_programare LIKE '%ora%' THEN
                    TRY_CAST(LEFT(Durata_programare, CHARINDEX(' ', Durata_programare) - 1) AS INT) * 60
                    +
                    TRY_CAST(
                        SUBSTRING(
                            Durata_programare,
                            CHARINDEX('si', Durata_programare) + 3,
                            CHARINDEX('minute', Durata_programare) - (CHARINDEX('si', Durata_programare) + 3)
                        ) AS INT
                    )
                ELSE
                    TRY_CAST(REPLACE(Durata_programare, ' min', '') AS INT)
            END
            >
            (
                SELECT AVG(
                    CASE
                        WHEN Durata_programare LIKE '%ora%' THEN
                            TRY_CAST(LEFT(Durata_programare, CHARINDEX(' ', Durata_programare) - 1) AS INT) * 60
                            +
                            TRY_CAST(
                                SUBSTRING(
                                    Durata_programare,
                                    CHARINDEX('si', Durata_programare) + 3,
                                    CHARINDEX('minute', Durata_programare) - (CHARINDEX('si', Durata_programare) + 3)
                                ) AS INT
                            )
                        ELSE
                            TRY_CAST(REPLACE(Durata_programare, ' min', '') AS INT)
                    END
                )
                FROM Programare
            )
        """;

        return jdbcTemplate.query(sql, this::mapRow);
    }

    public List<ProgramareDto> findProgramariDinAn(int an) {

        String sql = """
        SELECT
            pr.ProgramareID,
            pr.Data_programare,
            pr.Durata_programare,
            pa.Nume   AS PacientNume,
            pa.Prenume AS PacientPrenume,
            m.Nume    AS MedicNume,
            m.Prenume AS MedicPrenume
        FROM Programare pr
        JOIN Pacient pa ON pa.PacientID = pr.PacientID
        JOIN Medic m    ON m.MedicID    = pr.MedicID
        WHERE YEAR(pr.Data_programare) = ?
    """;

        return jdbcTemplate.query(sql, new Object[]{an}, (rs, rowNum) ->
                ProgramareDto.builder()
                        .programareId(rs.getInt("ProgramareID"))
                        .dataProgramare(rs.getTimestamp("Data_programare").toLocalDateTime())
                        .durataProgramare(rs.getString("Durata_programare"))
                        .pacientNume(
                                rs.getString("PacientNume") + " " +
                                        rs.getString("PacientPrenume"))
                        .medicNume(
                                rs.getString("MedicNume") + " " +
                                        rs.getString("MedicPrenume"))
                        .build()
        );
    }

}
