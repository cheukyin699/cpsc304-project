package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;
import org.cpsc304.bigdata.model.People.Patient;
import org.cpsc304.bigdata.model.People.UserInfo;
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

@Component
public class PatientDAOImpl implements PatientDAO {

    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public Patient findPatientById(final String Id) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Patient WHERE ID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1,Id);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Patient(
                        Id,
                        set.getString("Name"),
                        set.getString("Family_History"),
                        Integer.parseInt(set.getString("Age")),
                        Integer.parseInt(set.getString("Sex")),
                        set.getString("P_Username"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public List<MedicalRecord> findAssociatedMedicalRecords(final String Id) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Patient, MedicalRecords WHERE ID=PatientID AND ID=?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1,Id);
            final ResultSet set = statement.executeQuery();
            List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
            while (set.next()) {
                medicalRecords.add(
                        new MedicalRecord(
                        Id,
                        set.getDate("Start_date"),
                        set.getDate("End_date"),
                        set.getString("Disease"),
                        set.getString("Implant_Surgeries"),
                        set.getString("Allergies"),
                        set.getString("Medication")));
            }
            if (medicalRecords.isEmpty()) {
                return null;
            } else {
                return medicalRecords;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }


    @Override
    public void addPatient(String Id, String name, String familyHistory, int age, int sex) {
        final Connection connection = handler.getConnection();
        final String q = "INSERT INTO Patient VALUES(?, ?, ?, age, sex)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, Id);
            statement.setString(1, name);
            statement.setString(1, familyHistory);
            final ResultSet set = statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void clearMedicalRecords(final String Id) {
        final Connection connection = handler.getConnection();
        final String q = "DELETE FROM MedicalRecord WHERE PatientID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, Id);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public int countMedicalRecords(final String Id) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT COUNT(*) FROM MedicalRecord WHERE ID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, Id);
            final ResultSet set = statement.executeQuery();
            return set.getInt(1);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }


}
