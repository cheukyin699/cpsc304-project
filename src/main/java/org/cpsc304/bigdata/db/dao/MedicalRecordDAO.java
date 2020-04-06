package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;

import java.util.List;

public interface MedicalRecordDAO {
    void add(final MedicalRecord medicalRecord);

    void updateMedications(final String id, final String start, final String medication);

    List<MedicalRecord> searchByPatientId(final String id);
}
