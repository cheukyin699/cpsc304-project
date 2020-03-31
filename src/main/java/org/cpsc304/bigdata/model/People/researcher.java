package org.cpsc304.bigdata.model.People;

import org.cpsc304.bigdata.model.People.user;

public class researcher extends user {

    private String institute;
    private int numOfPublication;

    public researcher(String username, String name, String password, String specialty, String dept) {
        super(username, name, password, specialty, dept);
    }

    public researcher(String username, String name, String password, String specialty, String dept, String institute, int numOfPublication){
        super(username, name, password, specialty, dept);
        this.institute = institute;
        this.numOfPublication = numOfPublication;
    }

    public int getNumOfPublication() {
        return numOfPublication;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setNumOfPublication(int numOfPublication) {
        this.numOfPublication = numOfPublication;
    }
}
