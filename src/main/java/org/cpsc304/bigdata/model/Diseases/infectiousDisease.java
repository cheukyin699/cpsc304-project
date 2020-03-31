package org.cpsc304.bigdata.model.Diseases;

public class infectiousDisease extends disease {

    private String transmissionRoute;

    public infectiousDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public infectiousDisease(String name, int prevalence, String symptoms, String transmissionRoute) {
        super(name, prevalence, symptoms);
        this.transmissionRoute = transmissionRoute;
    }

    public String getTransmissionRoute() {return transmissionRoute; }

    public void setTransmissionRoute(String transmissionRoute) {this.transmissionRoute = transmissionRoute; }
}
