package org.cpsc304.bigdata.model.MedicalInfo;

public class ClinicalTrial {
    private String trialName;
    private String type;
    private int numParticipants;
    private int isComplete;

    public ClinicalTrial(String trialName, String type, int numParticipants, int isComplete) {
        this.trialName = trialName;
        this.type = type;
        this.numParticipants = numParticipants;
        this.isComplete = isComplete;
    }

    public String getTrialName() {return trialName; }
    public String getType() {return type;}
    public int getNumParticipants() {return numParticipants;}
    public int getIsComplete(){return isComplete;}

    public void setTrialName(String trialName){this.trialName = trialName;}
    public void setType(String type) {this.type = type;}
    public void setNumParticipants(int numParticipants) {this.numParticipants = numParticipants;}
    public void setIsComplete(int isComplete) {this.isComplete = isComplete; }
}
