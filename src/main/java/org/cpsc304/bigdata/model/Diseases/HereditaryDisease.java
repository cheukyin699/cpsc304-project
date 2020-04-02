package org.cpsc304.bigdata.model.Diseases;

public class HereditaryDisease extends Disease {

    private String heritancePattern;
    private String gene;

    public HereditaryDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public HereditaryDisease(String name, String heritancePattern, String gene) {
        super(name);
        this.heritancePattern = heritancePattern;
        this.gene = gene;
    }

    public String getHeritancePattern() {return heritancePattern; }
    public String getGene() {return gene; }

    public void setHeritancePattern(String heritancePattern) {this.heritancePattern = heritancePattern; }
    public void setGene(String gene) {this.gene = gene; }

}
