package org.cpsc304.bigdata.db.dao;

import java.util.List;

public interface PatientDAO {
    void findPatientById(final String Id);
    List<String> findAssociatedMedicalRecords();
    void addPatient();

    /**
     * Clears all medical history from a Patient. Not sure why you'd want to do that, but
     * it's there for you just in case you need it.
     */
    void clearMedicalRecords();
    void countMedicalRecords();
}
