package org.cpsc304.bigdata.model.People;

public class Physician extends UserInfo {

    private String hospital;

    public Physician(String username, String name, String password, String specialty, String dept) {
        super(username, name, password, specialty, dept);
    }

    public Physician(String username, String name, String password, String speciality, String dept, String hospital) {
        super(username, name, password, speciality, dept);
        this.hospital = hospital;
    }
}
