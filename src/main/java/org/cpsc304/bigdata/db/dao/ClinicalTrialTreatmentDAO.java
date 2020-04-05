package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.MedicalInfo.ClinicalTrial;
import org.cpsc304.bigdata.model.MedicalInfo.Treatment;

import java.util.HashMap;
import java.util.List;


public interface ClinicalTrialTreatmentDAO {
    void add(final ClinicalTrial trial, final Treatment treatment);
    void delete(final String ctname);

    List<ClinicalTrial> findClinicalTrialByName(final String name);
    List<Treatment> findTreatmentByName(final String name);

    List<ClinicalTrial> findClinicalTrailByDisease(final String dname);
    List<Treatment> findTreatmentByDisease(final String dname);

    List<ClinicalTrial> findAllClinicalTrailName();
    List<ClinicalTrial> filterby(final String field);


}
