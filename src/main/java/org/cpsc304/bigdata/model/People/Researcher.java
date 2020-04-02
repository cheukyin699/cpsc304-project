package org.cpsc304.bigdata.model.People;

public class Researcher extends UserInfo {

    private String institute;
    private int numOfPublication;

    public Researcher(String username, String name, String password, String specialty, String dept) {
        super(username, name, password, specialty, dept);
    }

    public Researcher(String username, String name, String password, String specialty, String dept, String institute, int numOfPublication){
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
