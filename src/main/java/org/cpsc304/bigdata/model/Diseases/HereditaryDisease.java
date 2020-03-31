package org.cpsc304.bigdata.model.Diseases;

public class HereditaryDisease extends Disease {

    private String heritancePattern;
    private String gene;
    private int genderAffected;

    public HereditaryDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public HereditaryDisease(String name, int prevalence, String symptoms, String heritancePattern, String gene, int genderAffected) {
        super(name, prevalence, symptoms);
        this.heritancePattern = heritancePattern;
        this.gene = gene;
        this.genderAffected = genderAffected;
    }

    public String getHeritancePattern() {return heritancePattern; }
    public String getGene() {return gene; }
    public int getGenderAffected() {return genderAffected; }

    public void setHeritancePattern(String heritancePattern) {this.heritancePattern = heritancePattern; }
    public void setGene(String gene) {this.gene = gene; }
    public void setGenderAffected(int genderAffected) {this.genderAffected = genderAffected; }
}
