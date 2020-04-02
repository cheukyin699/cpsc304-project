package org.cpsc304.bigdata.model.People;

public class UserInfo {

    private String username;
    private String name;
    private String password;
    private String specialty;
    private String department;

    public UserInfo(String username, String name, String password, String specialty, String dept){
        this.username = username;
        this.name = name;
        this.password = password;
        this.specialty = specialty;
        this.department = dept;
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
        return department;
    }

    public String getSpe() {
        return specialty;
    }
}
