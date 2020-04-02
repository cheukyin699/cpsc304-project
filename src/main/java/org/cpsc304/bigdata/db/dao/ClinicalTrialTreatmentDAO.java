package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.MedicalInfo.ClinicalTrial;
import org.cpsc304.bigdata.model.MedicalInfo.Treatment;

public interface ClinicalTrialTreatmentDAO {
    void add(final ClinicalTrial trial, final Treatment treatment);

    ClinicalTrial findClinicalTrialByName(final String name);
    Treatment findTreatmentByName(final String name);
}
