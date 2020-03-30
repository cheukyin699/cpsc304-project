package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.UserInfo;

import java.util.List;

public interface PatientDAO {
    void findPatientById(final String Id);
    List<UserInfo> findAssociatedMedicalRecords();
    void addPatient();

    /**
     * Clears all medical history from a patient. Not sure why you'd want to do that, but
     * it's there for you just in case you need it.
     */
    void clearMedicalRecords();
    void countMedicalRecords();
}
