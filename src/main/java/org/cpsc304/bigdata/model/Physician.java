package org.cpsc304.bigdata.model;

public class Physician extends UserInfo {

    private String hospital;

    public Physician(String username, String name, String password, String specialty, String dept) {
        super(username, name, password, specialty, dept);
    }

    public Physician(String username, String name, String password, String specialty, String dept, String hospital) {
        super(username, name, password, specialty, dept);
        this.hospital = hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospital() {
        return hospital;
    }
}
