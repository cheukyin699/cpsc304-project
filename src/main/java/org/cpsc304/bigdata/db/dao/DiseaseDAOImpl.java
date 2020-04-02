package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.Diseases.*;
import org.cpsc304.bigdata.model.MedicalInfo.ClinicalTrial;
import org.cpsc304.bigdata.model.People.Patient;
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
public class DiseaseDAOImpl implements DiseaseDAO{
    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<Disease> findDiseaseBySymptom(final String symptom) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Disease WHERE (Symptoms LIKE ?) OR (Symptoms LIKE ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            String lowerCase = "%" + symptom.substring(0,1).toUpperCase() + symptom.substring(1) + "%";
            String upperCase = "%" + symptom.substring(0,1).toLowerCase() + symptom.substring(1) + "%";
            statement.setString(1,lowerCase);
            statement.setString(2,upperCase);
            final ResultSet set = statement.executeQuery();
            List<Disease> diseases = new ArrayList<>();
            while (set.next()) {
                diseases.add(
                        new Disease(
                        set.getString("Name"),
                        set.getInt("Prevalence"),
                        set.getString("Symptoms")));
            }
            if (diseases.isEmpty()) {
                return null;
            } else {
                return diseases;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }



    @Override
    public void linkDiseaseToClinicalTrial(final Disease disease, final ClinicalTrial clinicalTrial) {
        final Connection connection = handler.getConnection();
        final String q1 = "INSERT INTO Disease VALUES(?, ?, ?)";
        final String q2 = "INSERT INTO ClinicalTrial VALUES(?, ?, ?, ?,)";
        final String q3 = "INSERT INTO Disease_ClinicalTrial VALUES(?, ?)";

        try {
            final PreparedStatement statement = connection.prepareStatement(q1);
            statement.setString(1, disease.getName());
            statement.setString(2, Integer.toString(disease.getPrevalence()));
            statement.setString(3, disease.getSymptoms());
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        try {
            final PreparedStatement statement = connection.prepareStatement(q2);
            statement.setString(1, clinicalTrial.getTrialName());
            statement.setString(2, clinicalTrial.getType());
            statement.setString(3, Integer.toString(clinicalTrial.getNumParticipants()));
            statement.setString(4, Integer.toString(clinicalTrial.getIsComplete()));
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        try {
            final PreparedStatement statement = connection.prepareStatement(q3);
            statement.setString(1, disease.getName());
            statement.setString(2, clinicalTrial.getTrialName());
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }




    @Override
    public DeficiencyDisease toDeficiencyDisease(final Disease disease) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM DeficiencyDisease WHERE Name = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, disease.getName());
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new DeficiencyDisease(
                        set.getString("Name"),
                        set.getString("DietaryElements")
                );
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return null;
    }


    @Override
    public HereditaryDisease toHereditaryDisease(Disease disease) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM HereditaryDisease WHERE Name = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, disease.getName());
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new HereditaryDisease(
                        set.getString("Name"),
                        set.getString("HeritancePattern"),
                        set.getString("Genes")
                );
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public PhysiologicalDisease toPhysiologicalDisease(Disease disease) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM PhysiologicalDisease WHERE Name = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, disease.getName());
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new PhysiologicalDisease(
                        set.getString("Name"),
                        set.getString("AssociatedArea")
                );
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public InfectiousDisease toInfectiousDisease(Disease disease) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM InfectiousDisease WHERE Name = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, disease.getName());
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new InfectiousDisease(
                        set.getString("Name"),
                        set.getString("TransmissionRoute")
                );
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
        return null;
    }
}
