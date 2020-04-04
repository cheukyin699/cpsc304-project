package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.MedicalInfo.ClinicalTrial;
import org.cpsc304.bigdata.model.MedicalInfo.Treatment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class ClinicalTrialTreatmentDAOImpl implements ClinicalTrialTreatmentDAO {

    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(ClinicalTrialTreatmentDAOImpl.class);

    @Override
    public void add(ClinicalTrial trial, Treatment treatment) {
        final Connection connection = handler.getConnection();
        final String q1 = "INSERT INTO ClinicalTrial_Treatment VALUES (?, ?)";
        final String q2 = "INSERT INTO ClinicalTrial VALUES (?, ?, ?, ?)";
        final String q3 = "INSERT INTO Treatment VALUES (?, ?, ?, ?)";
        try {
            // Treatment
            PreparedStatement statement = connection.prepareStatement(q3);
            statement.setString(1, treatment.getTreatmentName());
            statement.setFloat(2, treatment.getEfficiency());
            statement.setString(3, treatment.getEquipment());
            statement.setString(4, treatment.getRisks());
            statement.execute();
            // ClinicalTrial
            statement = connection.prepareStatement(q2);
            statement.setString(1, trial.getTrialName());
            statement.setString(2, trial.getType());
            statement.setInt(3, trial.getNumParticipants());
            statement.setInt(4, trial.getIsComplete());
            statement.execute();
            // ClinicalTrial_Treatment combo
            statement = connection.prepareStatement(q1);
            statement.setString(1, trial.getTrialName());
            statement.setString(2, treatment.getTreatmentName());
            statement.execute();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void delete(String ctname) {
        final Connection connection = handler.getConnection();
        final String q = "DELETE FROM ClinicalTrial WHERE TrialName = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, ctname);
            final int ups = statement.executeUpdate();
            if (ups == 0) {
                logger.warn("{} wasn't deleted properly", ctname);
            } else {
                logger.info("{} removed {} times", ctname, ups);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public List<ClinicalTrial> findClinicalTrialByName(String name) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM ClinicalTrial WHERE TrialName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            List<ClinicalTrial> clinicalTrials = new ArrayList<>();
            if (set.next()) {
                clinicalTrials.add(new ClinicalTrial(
                                        name,
                                        set.getString("Type"),
                                        set.getInt("Num_Participants"),
                                        set.getInt("IsComplete")));
                return clinicalTrials;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public Treatment findTreatmentByName(String name) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Treatment WHERE TreatmentName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Treatment(
                        name,
                        set.getFloat("Efficiency"),
                        set.getString("Equipment"),
                        set.getString("Risks"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        return null;
    }

    @Override
    public List<ClinicalTrial> findClinicalTrailByDisease(String dname) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM ClinicalTrial C INNER JOIN Disease_ClinicalTrial T ON " +
                "C.TrialName = T.CTName WHERE (T.DName LIKE ?) OR (T.DName LIKE ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            String lowerCase = "%" + dname.substring(0, 1).toUpperCase() + dname.substring(1) + "%";
            String upperCase = "%" + dname.substring(0, 1).toLowerCase() + dname.substring(1) + "%";
            statement.setString(1, lowerCase);
            statement.setString(2, upperCase);
            final ResultSet set = statement.executeQuery();
            List<ClinicalTrial> clinicalTrails = new ArrayList<>();
            while (set.next()) {
                clinicalTrails.add(
                        new ClinicalTrial(
                                set.getString("TrialName"),
                                set.getString("Type"),
                                set.getInt("Num_Participants"),
                                set.getInt("IsComplete")
                                ));
            }
            return clinicalTrails;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
