package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.Diseases.DeficiencyDisease;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.Diseases.HereditaryDisease;
import org.cpsc304.bigdata.model.Diseases.InfectiousDisease;
import org.cpsc304.bigdata.model.Diseases.PhysiologicalDisease;
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
public class DiseaseDAOImpl implements DiseaseDAO {
    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<Disease> findDiseaseBySymptom(final String symptom) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Disease WHERE (Symptoms LIKE ?) OR (Symptoms LIKE ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            String lowerCase = "%" + symptom.substring(0, 1).toUpperCase() + symptom.substring(1) + "%";
            String upperCase = "%" + symptom.substring(0, 1).toLowerCase() + symptom.substring(1) + "%";
            statement.setString(1, lowerCase);
            statement.setString(2, upperCase);
            final ResultSet set = statement.executeQuery();
            List<Disease> diseases = new ArrayList<>();
            while (set.next()) {
                diseases.add(
                        new Disease(
                                set.getString("Name"),
                                set.getInt("Prevalence"),
                                set.getString("Symptoms")));
            }
            return diseases;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();  
        }
    }

    @Override
    public List<Disease> findDiseaseByClinicalTrialName(String name) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM Disease D INNER JOIN Disease_ClinicalTrial T ON " +
                "D.Name = T.DName WHERE (T.CTName LIKE ?) OR (T.CTName LIKE ?)";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            String lowerCase = "%" + name.substring(0, 1).toUpperCase() + name.substring(1) + "%";
            String upperCase = "%" + name.substring(0, 1).toLowerCase() + name.substring(1) + "%";
            statement.setString(1, lowerCase);
            statement.setString(2, upperCase);
            final ResultSet set = statement.executeQuery();
            List<Disease> diseases = new ArrayList<>();
            while (set.next()) {
                diseases.add(
                        new Disease(
                                set.getString("Name"),
                                set.getInt("Prevalence"),
                                set.getString("Symptoms")));
            }
            return diseases;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Disease> findDiseaseByPara(final String table, final String number) {
        final Connection connection = handler.getConnection();
        try {
            final String q;
            final PreparedStatement statement;
            if(table.equalsIgnoreCase("Treatment")){
                q = "SELECT D.Name, D.Prevalence, D. Symptoms FROM Disease D INNER JOIN Disease_Treatment DT ON D.Name = DT.DName GROUP BY D.Name, D.Prevalence, D. Symptoms HAVING COUNT(*) >= ?";
                statement = connection.prepareStatement(q);
                statement.setString(1,number);
            }else{
                q = "SELECT D.Name, D.Prevalence, D. Symptoms FROM Disease D INNER JOIN Disease_ClinicalTrial DC ON D.Name = DC.DName GROUP BY D.Name, D.Prevalence, D. Symptoms HAVING COUNT(*) >= ?";
                statement = connection.prepareStatement(q);
                statement.setString(1, number);
            }

            List<Disease> diseases = new ArrayList<>();
            final ResultSet set = statement.executeQuery();
            while (set.next()){
                diseases.add(new Disease(
                        set.getString("Name"),
                        set.getInt("Prevalence"),
                        set.getString("Symptoms")));
            }
            return diseases;

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return Collections.emptyList();

    }


    @Override
    public void linkDiseaseToClinicalTrial(final String diseaseName, final String trialName) {
        final Connection connection = handler.getConnection();
        final String q = "INSERT INTO Disease_ClinicalTrial VALUES(?, ?)";

        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, diseaseName);
            statement.setString(2, trialName);
            statement.executeQuery();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public void deleteDisease(String name) {
        final Connection connection = handler.getConnection();
        final String q = "DELETE FROM Disease WHERE Name = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, name);
            final int ups = statement.executeUpdate();
            if (ups == 0) {
                logger.warn("{} wasn't deleted properly", name);
            } else {
                logger.info("{} removed {} times", name, ups);
            }
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
