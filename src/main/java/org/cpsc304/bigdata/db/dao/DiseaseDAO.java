package org.cpsc304.bigdata.db.dao;

public interface DiseaseDAO {
    void findDiseaseBySymptom(final String symptom);
    void linkDiseaseToClinicalTrial();

    /**
     * Converts a given Disease into a more specific type of Disease.
     */
    void toDeficiencyDisease();
    void toHereditaryDisease();
    void toPhysiologicalDisease();
    void toInfectiousDisease();
}
