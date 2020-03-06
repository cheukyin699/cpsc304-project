package org.cpsc304.bigdata.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DatabaseConnectionHandler {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1522:stu";

    private Connection connection;
    private Logger logger = LoggerFactory.getLogger(DatabaseConnectionHandler.class);

    public DatabaseConnectionHandler() {
        this(
                System.getenv("USER"),
                System.getenv("PASS")
        );
    }

    public DatabaseConnectionHandler(final String user, final String pwd) {
        try {
            logger.debug("Registering Oracle JDBC driver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            if (!connect(user, pwd)) {
                logger.error("Could not connect to oracle with " + user + " and " + pwd);
            } else {
                logger.info("Connected to remote oracle database");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean connect(final String user, final String pwd) {
        try {
            logger.debug("Connecting to database");
            connection = DriverManager.getConnection(DB_URL, user, pwd);

            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Get version information of Oracle database as a string
     * @return Database version information
     */
    String getVersion() {
        if (connection == null) {
            return "Not connected to database.";
        }

        try {
            PreparedStatement sql = connection.prepareStatement("SELECT banner FROM v$version");
            ResultSet rs = sql.executeQuery();

            if (rs.next()) {
                return rs.getString("banner");
            } else {
                return "Cannot find banner";
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return "SQL error: please view the logs.";
        }
    }
}
