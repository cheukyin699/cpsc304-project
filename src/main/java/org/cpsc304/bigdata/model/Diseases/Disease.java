package org.cpsc304.bigdata.model.Diseases;

public class Disease {
    private String name;
    private int prevalence;
    private String symptoms;

    public Disease(String name, int prevalence, String symptoms) {
        this.name = name;
        this.prevalence = prevalence;
        this.symptoms = symptoms;
    }

    public Disease(String name) {
        this.name = name;
    }

    public String getName() {return name; }
    public int getPrevalence() {return prevalence; }
    public String getSymptoms() {return symptoms; }

    public void setName(String name) {this.name = name; }
    public void setPrevalence(int prevalence) {this.prevalence = prevalence; }
    public void setSymptoms(String symptoms) {this.symptoms = symptoms; }
}
