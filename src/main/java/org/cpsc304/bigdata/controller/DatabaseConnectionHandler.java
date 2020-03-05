package org.cpsc304.bigdata.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionHandler {

    private static final String DB_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";

    private Connection connection;

    public DatabaseConnectionHandler(final String user, final String pwd) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

            if (!connect(user, pwd)) {
                System.out.println("Could not connect to oracle with username and password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean connect(final String user, final String pwd) {
        try {
            connection = DriverManager.getConnection(DB_URL, user, pwd);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
