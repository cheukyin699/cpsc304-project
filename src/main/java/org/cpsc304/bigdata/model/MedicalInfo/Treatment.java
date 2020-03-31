package org.cpsc304.bigdata.model.MedicalInfo;

public class Treatment {
    private String treatmentName;
    private float efficiency;
    private int cost;
    private String equipment;
    private String risks;


    public Treatment(String treatmentName, float efficiency, int cost, String equipment, String risks){
        this.treatmentName = treatmentName;
        this.efficiency = efficiency;
        this.cost = cost;
        this.equipment = equipment;
        this.risks = risks;
    }

    public String getTreatmentName() {return treatmentName; }
    public float getEfficiency() {return efficiency; }
    public int getCost() {return cost; }
    public String getEquipment() {return equipment; }
    public String getRisks() {return risks; }

    public void setTreatmentName(String treatmentName) {this.treatmentName = treatmentName; }
    public void setEfficiency(float efficiency) {this.efficiency = efficiency; }
    public void setCost(int cost) {this.cost = cost; }
    public void setEquipment(String equipment) {this.equipment = equipment; }
    public void setRisks(String risks) {this.risks = risks; }
}
