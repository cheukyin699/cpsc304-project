package org.cpsc304.bigdata.model.MedicalInfo;

public class ClinicalTrial {
    private String trialName;
    private float duration;
    private String type;
    private String risk;
    private int numParticipants;
    private int isComplete;

    public ClinicalTrial(String trialName, float duration, String type, String risk, int numParticipants, int isComplete){
        this.trialName = trialName;
        this.duration = duration;
        this.type = type;
        this.risk = risk;
        this.numParticipants = numParticipants;
        this.isComplete = isComplete;
    }

    public String getTrialName() {return trialName; }
    public float getDuration() {return duration;}
    public String getType() {return type;}
    public String getRisk() {return risk;}
    public int getNumParticipants() {return numParticipants;}
    public int getIsComplete(){return isComplete;}

    public void setTrialName(String trialName){this.trialName = trialName;}
    public void setDuration(float duration){this.duration = duration;}
    public void setType(String type) {this.type = type;}
    public void setNumParticipants(int numParticipants) {this.numParticipants = numParticipants;}
    public void setRisk(String risk) {this.risk = risk; }
    public void setIsComplete(int isComplete) {this.isComplete = isComplete; }
}
