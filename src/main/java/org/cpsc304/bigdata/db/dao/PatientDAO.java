package org.cpsc304.bigdata.db.dao;

import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;
import org.cpsc304.bigdata.model.People.Patient;
import org.cpsc304.bigdata.model.People.Physician;

import java.util.List;
import java.util.Map;

public interface PatientDAO {
    Patient findPatientById(final String Id);
    List<MedicalRecord> findAssociatedMedicalRecords(final String Id);
    List<Patient> findOldestPatientsPerPhysicians();
    List<Patient> findPatientsAllDiseases();


    void addPatient(final Patient patient);
    void deletePatient(final String id);

    /**
     * Clears all medical history from a Patient. Not sure why you'd want to do that, but
     * it's there for you just in case you need it.
     */
    void clearMedicalRecords(final String Id);
    int countMedicalRecords(final String Id);
}
