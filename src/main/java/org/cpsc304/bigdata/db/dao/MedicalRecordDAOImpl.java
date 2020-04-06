package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Component
public class MedicalRecordDAOImpl implements MedicalRecordDAO{

    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public void add(MedicalRecord medicalRecord) {
        final Connection connection = handler.getConnection();
        final String q = "INSERT INTO MedicalRecord VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, medicalRecord.getPatientId());

            statement.setString(2, medicalRecord.getStartDate().toString());
            statement.setString(3, medicalRecord.getEndDate().toString());

            statement.setString(4, medicalRecord.getDisease());
            statement.setString(5, medicalRecord.getImplants_sur());
            statement.setString(6, medicalRecord.getAllergies());
            statement.setString(7, medicalRecord.getMed());
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void updateMedications(String id, String start, String medication) {
        final Connection connection = handler.getConnection();
        final String q = "UPDATE MedicalRecord SET Medication = concat(Medication, ?) WHERE PatientID = ? AND Start_date = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, ", " + medication);
            statement.setString(2, id);
            statement.setString(3, start);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public List<MedicalRecord> searchByPatientId(String id) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM MedicalRecord WHERE PatientID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, id);
            final ResultSet set =  statement.executeQuery();
            List<MedicalRecord> records = new ArrayList<>();
            while (set.next()) {
                records.add(
                        new MedicalRecord(
                                set.getString("PatientID"),
                                set.getDate("Start_date").toString(),
                                set.getDate("End_date").toString(),
                                set.getString("Disease"),
                                set.getString("Implants_Surgeries"),
                                set.getString("Allergies"),
                                set.getString("Medication")
                        )
                );
            }
            return records;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
