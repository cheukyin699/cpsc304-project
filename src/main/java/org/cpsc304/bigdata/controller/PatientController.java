package org.cpsc304.bigdata.controller;

import org.cpsc304.bigdata.db.dao.DiseaseDAO;
import org.cpsc304.bigdata.db.dao.MedicalRecordDAO;
import org.cpsc304.bigdata.db.dao.PatientDAO;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.cpsc304.bigdata.model.People.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Controller
@RestController
public class PatientController {

    @Autowired
    private PatientDAO patientDAO;
    private Logger logger = LoggerFactory.getLogger(PatientController.class);


    @GetMapping("/patient")
    public List<Patient> patientsWithAllDisease(
            @RequestParam(value = "id", required = false) final String id) {

        if (id != null) {
            return patientDAO.findPatientById(id);
        } else {
            return patientDAO.findPatientsAllDiseases();
        }

    }

    @GetMapping("/patient/count/{patientid}")
    public int countMedicalRecords(@PathVariable("patientid") final String pid) {
        logger.info("{}", pid);
        return patientDAO.countMedicalRecords(pid);
    }

    @GetMapping("/patient/oldest")
    public List<Patient> oldestPatientByPhysician() {
        return patientDAO.findOldestPatientsPerPhysicians();
    }


    @DeleteMapping("/patient/{id}")
    public String deleteDisease(@PathVariable("id") final String id) {
        logger.info("{}", id);
        patientDAO.deletePatient(id);
        return "ok";
    }
}
