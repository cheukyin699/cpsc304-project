package org.cpsc304.bigdata.model.People;

public class Patient {
    private String id;
    private String patientName;
    private String familyHistory;
    private int age;
    private int sex;
    private String physicianName;

    public Patient(String id, String name, String familyHistory, int age, int sex, String physicianName) {
        this.id = id;
        this.patientName = name;
        this.familyHistory = familyHistory;
        this.age = age;
        this.sex = sex;
        this.physicianName = physicianName;
    }

    public String getId() { return id; }
    public String getpatientName() {return patientName; }
    public String getFamilyHistory() {return familyHistory; }
    public int getAge() {return age; }
    public int getSex() { return sex; }
    public String getPhysicianName() {return physicianName; }

    public void setId(String id) {this.id = id; }
    public void setPatientName(String patientName) {this.patientName = patientName; }
    public void setFamilyHistory(String familyHistory) {this.familyHistory = familyHistory; }
    public void setAge(int age) {this.age = age; }
    public void setSex(int sex) {this.sex = sex; }
    public void setPhysicianName(String physicianName) { this.physicianName = physicianName; }
}
