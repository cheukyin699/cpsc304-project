package org.cpsc304.bigdata.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DatabaseConnectionHandler implements DataSource {

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
//                DatabaseInitializer.initDatabase(connection);
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
    public String getVersion() {
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

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String s, String s1) {
        return connection;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) {

    }

    @Override
    public void setLoginTimeout(int i) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public java.util.logging.Logger getParentLogger() {
        return (java.util.logging.Logger) logger;
    }


}
