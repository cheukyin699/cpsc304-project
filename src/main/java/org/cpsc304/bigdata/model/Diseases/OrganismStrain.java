package org.cpsc304.bigdata.model.Diseases;

public class OrganismStrain {
    private String strainName;
    private String familyName;
    private String acronym;

    public OrganismStrain(String strainName, String familyName, String acronym) {
        this.strainName = strainName;
        this.familyName = familyName;
        this.acronym = acronym;
    }

    public String getStrainName() { return strainName; }
    public String getFamilyName() { return familyName; }
    public String getAcronym() { return acronym; }

    public void setStrainName(String strainName) { this.strainName = strainName; }
    public void setFamilyName(String familyName) {this.familyName = familyName; }
    public void setAcronym(String acronym) {this.acronym = acronym; }
}
