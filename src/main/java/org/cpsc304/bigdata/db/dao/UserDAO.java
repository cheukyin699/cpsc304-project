package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.People.Physician;
import org.cpsc304.bigdata.model.People.Researcher;
import org.cpsc304.bigdata.model.People.UserInfo;
import org.springframework.stereotype.Component;

@Component
public interface UserDAO {
    UserInfo findUserFromUsername(final String username);
    Physician findPhysicianFromUsername(final String username);
    Researcher findResearcherFromUsername(final String username);

    void deleteUser(final String username);
}
