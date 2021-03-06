package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;
import org.cpsc304.bigdata.model.People.Patient;
import org.cpsc304.bigdata.model.People.Physician;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PatientDAOImpl implements PatientDAO {

    @Autowired
    private DatabaseConnectionHandler handler;
    @Autowired
    private UserDAO userDAO;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<Patient> findPatientById(final String Id) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Patient WHERE ID = ?";
        List patients = new ArrayList<>();
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1,Id);
            final ResultSet set = statement.executeQuery();
            String sex;
            if (set.next()) {
                sex = (set.getInt("Sex") == 0) ? "Male" : "Female";
                patients.add(new Patient(
                        Id,
                        set.getString("Name"),
                        set.getString("Family_History"),
                        set.getInt("Age"),
                        sex,
                        set.getString("P_Username"))
                );
            }
            return patients;
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
                        set.getDate("Start_date").toString(),
                        set.getDate("End_date").toString(),
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
    public List<Patient> findOldestPatientsPerPhysicians() {
        List<Patient> patients = new ArrayList<>();
        //HashMap<Physician, Patient> patients = new HashMap<>();
        final Connection connection = handler.getConnection();
        final String q = "SELECT * " +
                        "FROM Patient p1 " +
                        "WHERE p1.age = (SELECT MAX(p2.age) " +
                                        "FROM Patient p2 INNER JOIN Physician u " +
                                        "ON (p2.p_username = u.username AND p1.p_username = p2.p_username) " +
                                        "GROUP BY u.username)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            final ResultSet set = statement.executeQuery();
            String sex;
            while (set.next()) {
                // final Physician physician = userDAO.findPhysicianFromUsername(set.getString("Username"));
                sex = (set.getInt("Sex") == 0) ? "Male" : "Female";
                patients.add(new Patient(
                        set.getString("ID"),
                        set.getString("Name"),
                        set.getString("Family_History"),
                        set.getInt("Age"),
                        sex,
                        set.getString("P_Username"))
                );

//                if (physician != null) {
//                    m.put(physician, patient);
//                } else {
//                    logger.warn("Could not find physician '{}'", set.getString("Username"));
//                }
            }
            return patients;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void deletePatient(final String id) {
        final Connection connection = handler.getConnection();
        final String q = "DELETE FROM Patient WHERE ID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }


    @Override
    public void addPatient(final Patient patient) {
        final Connection connection = handler.getConnection();
        final String q = "INSERT INTO Patient VALUES(?, ?, ?, age, sex)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, patient.getId());
            statement.setString(2, patient.getpatientName());
            statement.setString(3, patient.getFamilyHistory());
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
        final String q = "SELECT COUNT(*) FROM MedicalRecord WHERE PatientID = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, Id);
            final ResultSet set = statement.executeQuery();
            int count = 0;
            if (set.next()) {
                count = set.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return -1;
        }
    }

    @Override
    public List<Patient> findPatientsAllDiseases() {
        final Connection connection = handler.getConnection();
        final String q = "SELECT DISTINCT p.id, p.name, p.family_history, p.age, p.sex, p.p_username " +
                        "FROM Patient p INNER JOIN SuffersFrom s ON p.id = s.p_id " +
                        "WHERE NOT EXISTS (SELECT d.name FROM Disease d " +
                                            "MINUS " +
                                            "SELECT s2.d_name " +
                                            "FROM Patient p2 INNER JOIN SuffersFrom s2 ON p2.id = s2.p_id " +
                                            "WHERE p.id = p2.id)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            final ResultSet set = statement.executeQuery();
            List<Patient> patients = new ArrayList<>();
            String sex;
            while (set.next()) {
                    sex = (set.getInt("Sex") == 0) ? "Male": "Female";

                patients.add(new Patient(
                        set.getString("ID"),
                        set.getString("Name"),
                        set.getString("Family_History"),
                        set.getInt("Age"),
                        sex,
                        set.getString("P_Username")
                    )
                );
            }
            return patients;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
