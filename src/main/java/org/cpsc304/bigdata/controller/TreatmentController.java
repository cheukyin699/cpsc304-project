package org.cpsc304.bigdata.controller;



import org.cpsc304.bigdata.db.dao.ClinicalTrialTreatmentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RestController
public class TreatmentController {

    @Autowired
    private ClinicalTrialTreatmentDAO clinicalTrialTreatmentDAO;
    private Logger logger = LoggerFactory.getLogger(TreatmentController.class);

    @GetMapping("/treatment")
    public Object getTreatment (
            @RequestParam(value = "tname", required = false) final String tname,
            @RequestParam(value = "dname", required = false) final String dname,
            @RequestParam(value = "table", required = false) final String table){

        if(tname != null){
            return clinicalTrialTreatmentDAO.findTreatmentByName(tname);
        }
        if(dname != null){
            return clinicalTrialTreatmentDAO.findTreatmentByDisease(dname);
        }


        return Collections.emptyList();
    }









}
