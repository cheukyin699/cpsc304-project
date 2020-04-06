package org.cpsc304.bigdata.controller;


import org.cpsc304.bigdata.db.dao.MedicalRecordDAO;
import org.cpsc304.bigdata.model.MedicalInfo.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;
    private Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @GetMapping("/medical_record")
    public List<MedicalRecord> searchMedicalRecord(@RequestParam(value = "patientid") final String pid){
        return medicalRecordDAO.searchByPatientId(pid);
    }

    @PostMapping("/medical_record/edit/{patientid}/{medications}/{start_date}")
    public String editMedicalRecords(@PathVariable("patientid") final String pid,
                                     @PathVariable("medications") final String medications,
                                     @PathVariable("start_date") final String start) {

        medicalRecordDAO.updateMedications(pid, start, medications);
        return "";
    }




}
