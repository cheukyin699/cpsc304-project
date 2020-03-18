package org.cpsc304.bigdata.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Helper static class that initializes the database every single time the application is started if there is
 * a valid connection.
 */
public class DatabaseInitializer {

    private static final String TABLES[] = {
            "User", "Physician", "Researcher",
            "Disease", "DeficiencyDisease", "HereditaryDisease", "PhysiologicalDisease", "InfectiousDisease",
            "InfectiousOrganism", "Strain",
            "Patient", "MedicalRecord",
            "SuffersFrom",
            "Treatment", "ClinicalTrial",
            "WorkOn", "DiagnosticTest", "DiagnosedBy"
    };

    private static Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    public static void initDatabase(final Connection connection) {
        logger.info("Initializing database");
        initTables(connection);
        populateTables(connection);
    }

    private static void initTables(final Connection connection) {
        deleteExistingTables(connection);
    }

    private static void deleteExistingTables(final Connection connection) {
        for (final String table : TABLES) {
            try {
                PreparedStatement statement = connection.prepareStatement("DROP TABLE " + table);
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.warn(e.getMessage().replace("\n", ""));
            }
        }
    }

    private static void populateTables(final Connection connection) {

    }
}
