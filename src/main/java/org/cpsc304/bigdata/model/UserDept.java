package org.cpsc304.bigdata.model;


public class UserDept {
    private String specialty;
    private String department;

    public UserDept(String specialty, String department){
        this.specialty = specialty;
        this.department = department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDepartment() {
        return department;
    }

    public String getSpecialty() {
        return specialty;
    }
}
