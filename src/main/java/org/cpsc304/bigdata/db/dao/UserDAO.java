package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO {
    UserInfo findUserFromUsername(final String username);
}
