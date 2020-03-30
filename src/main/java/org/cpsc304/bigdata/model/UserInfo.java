package org.cpsc304.bigdata.model;

public class UserInfo {

    private String username;
    private String name;
    private String password;
    private UserDept speDept;

    public UserInfo(String username, String name, String password, String specialty, String dept){
        this.username = username;
        this.name = name;
        this.password = password;
        speDept = new UserDept(specialty,dept);
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
