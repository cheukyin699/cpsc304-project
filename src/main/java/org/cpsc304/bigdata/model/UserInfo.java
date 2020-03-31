package org.cpsc304.bigdata.model.People;

<<<<<<< HEAD:src/main/java/org/cpsc304/bigdata/model/UserInfo.java
public class UserInfo {
=======
public abstract class user {
>>>>>>> master:src/main/java/org/cpsc304/bigdata/model/People/user.java

    private String username;
    private String name;
    private String password;
<<<<<<< HEAD:src/main/java/org/cpsc304/bigdata/model/UserInfo.java
    private UserDept speDept;

    public UserInfo(String username, String name, String password, String specialty, String dept){
        this.username = username;
        this.name = name;
        this.password = password;
        speDept = new UserDept(specialty,dept);
=======
    private String specialty;
    private String department;

    public user(String username, String name, String password, String specialty, String dept){
        this.username = username;
        this.name = name;
        this.password = password;
        this.specialty = specialty;
        this.department = dept;
>>>>>>> master:src/main/java/org/cpsc304/bigdata/model/People/user.java
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
