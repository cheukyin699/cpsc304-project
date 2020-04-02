package org.cpsc304.bigdata.model.Diseases;

public class InfectiousOrganism {
    private String familyName;
    private String infectiousDiseaseName;
    private String commonName;

    public InfectiousOrganism(String familyName, String infectiousDiseaseName, String commonName) {
        this.familyName = familyName;
        this.infectiousDiseaseName = infectiousDiseaseName;
        this.commonName = commonName;
    }

    public String getFamilyName() {return familyName; }
    public String getInfectiousDiseaseName() {return infectiousDiseaseName; }
    public String getCommonName() {return commonName; }

    public void setFamilyName(String familyName) {this.familyName = familyName; }
    public void setInfectiousDiseaseName(String infectiousDiseaseName) {this.infectiousDiseaseName = infectiousDiseaseName; }
    public void setCommonName(String commonName) {this.commonName = commonName; }
}
