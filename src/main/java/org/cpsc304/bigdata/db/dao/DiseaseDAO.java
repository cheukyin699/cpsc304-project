package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.Diseases.DeficiencyDisease;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.Diseases.HereditaryDisease;
import org.cpsc304.bigdata.model.Diseases.InfectiousDisease;
import org.cpsc304.bigdata.model.Diseases.PhysiologicalDisease;

public interface DiseaseDAO {
    Disease findDiseaseBySymptom(final String symptom);

    void linkDiseaseToClinicalTrial();

    /**
     * Converts a given Disease into a more specific type of Disease.
     */
    DeficiencyDisease toDeficiencyDisease(final Disease disease);
    HereditaryDisease toHereditaryDisease(final Disease disease);
    PhysiologicalDisease toPhysiologicalDisease(final Disease disease);
    InfectiousDisease toInfectiousDisease(final Disease disease);
}
