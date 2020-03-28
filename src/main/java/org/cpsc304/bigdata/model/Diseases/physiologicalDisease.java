package org.cpsc304.bigdata.model.Diseases;

public class physiologicalDisease extends disease {

    private String associatedArea;

    public physiologicalDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public physiologicalDisease(String name, int prevalence, String symptoms, String associatedArea) {
        super(name, prevalence, symptoms);
        this.associatedArea = associatedArea;
    }

    public String getAssociatedArea() {return associatedArea; }

    public void setAssociatedArea(String associatedArea) {this.associatedArea = associatedArea; }
}
