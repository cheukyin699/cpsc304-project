package org.cpsc304.bigdata.model.MedicalInfo;

import java.util.Date;

public class MedicalRecord {

    private String patientId;
    private String startDate;
    private String endDate;
    private String disease;
    private String implants_sur;
    private String allergies;
    private String med;

    public MedicalRecord(String patientId, String start, String end, String disease, String implants_sur, String allergies, String med) {
        this.patientId = patientId;
        this.startDate = start;
        this.endDate = end;
        this.disease = disease;
        this.implants_sur = implants_sur;
        this.allergies = allergies;
        this.med = med;
    }

    public String getPatientId() {return patientId; }
    public String getStartDate() {return startDate; }
    public String getEndDate() {return endDate; }
    public String getDisease() {return disease; }
    public String getImplants_sur() {return implants_sur; }
    public String getAllergies() {return allergies; }
    public String getMed() {return med; }


    public void setPatientId(String patientId) {this.patientId = patientId; }

    public void setStartDate(String startDate) {this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setDisease(String disease) {this.disease = disease; }
    public void setImplants_sur(String implants_sur) {this.implants_sur = implants_sur; }
    public void setAllergies(String allergies) {  this.allergies = allergies; }
    public void setMed(String med) { this.med = med; }

}
