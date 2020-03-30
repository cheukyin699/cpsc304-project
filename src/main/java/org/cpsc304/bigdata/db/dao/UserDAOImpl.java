package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.model.UserInfo;
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
            set.next();
            return new UserInfo(
                    username,
                    set.getString("Name"),
                    set.getString("Password"),
                    set.getString("Speciality"),
                    set.getString("Department"));
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}
