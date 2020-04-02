package org.cpsc304.bigdata.model.Diseases;

public class DeficiencyDisease extends Disease {

    private String dietaryElement;

    public DeficiencyDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public DeficiencyDisease(String name, String dietaryElement) {
        super(name);
        this.dietaryElement = dietaryElement;
    }

    public String getDietaryElement() {return dietaryElement; }

    public void setDietaryElement(String dietaryElement) {this.dietaryElement = dietaryElement; }
}
