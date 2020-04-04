package org.cpsc304.bigdata.controller;


import org.cpsc304.bigdata.db.dao.ClinicalTrialTreatmentDAO;
import org.cpsc304.bigdata.model.MedicalInfo.ClinicalTrial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class ClinicalTrialController {

    @Autowired
    private ClinicalTrialTreatmentDAO clinicalTrialTreatmentDAO;
    private Logger logger = LoggerFactory.getLogger(ClinicalTrialController.class);

    @GetMapping("/clinicalTrial")
    public List<ClinicalTrial> getClinicalTrial(
            @RequestParam(value = "ctname", required = false) final String ctname,
            @RequestParam(value = "dname", required = false) final String dname){

        if (ctname != null){
            return clinicalTrialTreatmentDAO.findClinicalTrialByName(ctname);
        }
        if (dname != null){
            return clinicalTrialTreatmentDAO.findClinicalTrailByDisease(dname);
        }

        return clinicalTrialTreatmentDAO.findAllClinicalTrailName();
    }

    @DeleteMapping("/clinicalTrial/{ctname}")
    public String deleteClinicalTrial(@PathVariable("ctname") final String ctname){
        clinicalTrialTreatmentDAO.delete(ctname);
        return "clinicalTrial removed";
    }

}
