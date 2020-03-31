package org.cpsc304.bigdata.model.Diseases;

import org.cpsc304.bigdata.model.Diseases.disease;

public class deficiencyDisease extends disease {

    private String dietaryElement;

    public deficiencyDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public deficiencyDisease(String name, int prevalence, String symptoms, String dietaryElement) {
        super(name, prevalence, symptoms);
        this.dietaryElement = dietaryElement;
    }

    public String getDietaryElement() {return dietaryElement; }

    public void setDietaryElement(String dietaryElement) {this.dietaryElement = dietaryElement; }
}
