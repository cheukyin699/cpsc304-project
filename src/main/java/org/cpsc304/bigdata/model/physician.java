package org.cpsc304.bigdata.model;

public class physician extends user_info {

    private String hospital;

    public physician(String username, String name, String password, String specialty, String dept) {
        super(username, name, password, specialty, dept);
    }

    public physician(String username, String name, String password, String specialty, String dept, String hospital) {
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
