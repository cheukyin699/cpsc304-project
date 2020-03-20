package org.cpsc304.bigdata.model;

public class user_info {

    private String username;
    private String name;
    private String password;
    private user_dept speDept;

    public user_info (String username, String name, String password, String specialty, String dept){
        this.username = username;
        this.name = name;
        this.password = password;
        speDept = new user_dept(specialty,dept);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDept() {
        return speDept.getDepartment();
    }

    public String getSpe() {
        return speDept.getSpecialty();
    }
}
