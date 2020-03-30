package org.cpsc304.bigdata.db.dao;

public interface ClinicalTrialTreatmentDAO {
    void add();

    void findClinicalTrialByName(final String name);
    void findTreatmentByName(final String name);
}
