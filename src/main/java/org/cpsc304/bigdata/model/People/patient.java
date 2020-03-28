package org.cpsc304.bigdata.model.People;

public class patient {
    private String id;
    private String patientName;
    private String address;
    private String familyHistory;
    private int age;
    private int sex;
    private String physicianName;

    public patient(String id, String name, String address, String familyHistory, int age, int sex, String physicianName) {
        this.id = id;
        this.patientName = name;
        this.address = address;
        this.familyHistory = familyHistory;
        this.age = age;
        this.sex = sex;
        this.physicianName = physicianName;
    }

    public String getId() { return id; }
    public String getpatientName() {return patientName; }
    public String getAddress() {return address; }
    public String getFamilyHistory() {return familyHistory; }
    public int getAge() {return age; }
    public int getSex() { return sex; }
    public String getPhysicianName() {return physicianName; }

    public void setId(String id) {this.id = id; }
    public void setPatientName(String patientName) {this.patientName = patientName; }
    public void setAddress(String address) {this.address = address; }
    public void setFamilyHistory(String familyHistory) {this.familyHistory = familyHistory; }
    public void setAge(int age) {this.age = age; }
    public void setSex(int sex) {this.sex = sex; }
    public void setPhysicianName(String physicianName) { this.physicianName = physicianName; }
}
