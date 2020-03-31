package org.cpsc304.bigdata.model.Diseases;

public class InfectiousDisease extends Disease {

    private String transmissionRoute;

    public InfectiousDisease(String name, int prevalence, String symptoms) {
        super(name, prevalence, symptoms);
    }

    public InfectiousDisease(String name, int prevalence, String symptoms, String transmissionRoute) {
        super(name, prevalence, symptoms);
        this.transmissionRoute = transmissionRoute;
    }

    public String getTransmissionRoute() {return transmissionRoute; }

    public void setTransmissionRoute(String transmissionRoute) {this.transmissionRoute = transmissionRoute; }
}
