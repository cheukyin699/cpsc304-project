package org.cpsc304.bigdata.model.Diseases;

public class PhysiologicalDisease extends Disease {

    private String associatedArea;

    public PhysiologicalDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public PhysiologicalDisease(String name,  String associatedArea) {
        super(name);
        this.associatedArea = associatedArea;
    }

    public String getAssociatedArea() {return associatedArea; }

    public void setAssociatedArea(String associatedArea) {this.associatedArea = associatedArea; }
}
