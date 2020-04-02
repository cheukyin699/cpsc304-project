package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;

public interface MedicalRecordDAO {
    void add(final MedicalRecord medicalRecord);

    void updateMedications(final String id, final String medication);
}
