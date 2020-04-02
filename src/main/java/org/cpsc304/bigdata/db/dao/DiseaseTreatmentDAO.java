package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.MedicalInfo.Treatment;

public interface DiseaseTreatmentDAO {
    void add(final Disease disease, final Treatment treatment);

    Disease findDiseaseByName(final String name);
    Treatment findTreatmentByName(final String name);
}
