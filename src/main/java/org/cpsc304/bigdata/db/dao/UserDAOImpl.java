package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.People.Physician;
import org.cpsc304.bigdata.model.People.Researcher;
import org.cpsc304.bigdata.model.People.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private DatabaseConnectionHandler handler;
    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public UserInfo findUserFromUsername(final String username) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM User_Info NATURAL JOIN User_Dept WHERE Username = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, username);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new UserInfo(
                        username,
                        set.getString("Name"),
                        set.getString("Password"),
                        set.getString("Speciality"),
                        set.getString("Department"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public Physician findPhysicianFromUsername(final String username) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM User_Info NATURAL JOIN User_Dept, Physician WHERE Username = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, username);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Physician(
                        username,
                        set.getString("Name"),
                        set.getString("Password"),
                        set.getString("Speciality"),
                        set.getString("Department"),
                        set.getString("Hospital"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public Researcher findResearcherFromUsername(final String username) {
        final Connection connection = handler.getConnection();
        final String q = "SELECT * FROM User_Info NATURAL JOIN User_Dept, Researcher WHERE Username = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, username);
            final ResultSet set = statement.executeQuery();
            if (set.next()) {
                return new Researcher(
                        username,
                        set.getString("Name"),
                        set.getString("Password"),
                        set.getString("Speciality"),
                        set.getString("Department"),
                        set.getString("Institute"),
                        set.getInt("NumOfPublications")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteUser(final String username) {
        final Connection connection = handler.getConnection();
        final String q = "DELETE FROM User_Info WHERE Username = ?";
        try {
            final PreparedStatement statement = connection.prepareStatement(q);
            statement.setString(1, username);
            logger.debug("Deleted {} objects", statement.executeUpdate());
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }
}
