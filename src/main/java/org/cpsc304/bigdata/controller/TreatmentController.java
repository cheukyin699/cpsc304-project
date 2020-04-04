package org.cpsc304.bigdata.controller;


import org.cpsc304.bigdata.db.dao.ClinicalTrialTreatmentDAO;
import org.cpsc304.bigdata.model.MedicalInfo.Treatment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;

@Controller
@RestController
public class TreatmentController {

    @Autowired
    private ClinicalTrialTreatmentDAO clinicalTrialTreatmentDAO;
    private Logger logger = LoggerFactory.getLogger(TreatmentController.class);

    @GetMapping("/treatment")
    public List<Treatment> getTreatment (
            @RequestParam(value = "tname", required = false) final String tname,
            @RequestParam(value = "dname", required = false) final String dname
            ){

        if(tname != null){
            return clinicalTrialTreatmentDAO.findTreatmentByName(tname);
        }
        if(dname != null){
            return clinicalTrialTreatmentDAO.findTreatmentByDisease(dname);
        }

        return Collections.emptyList();
    }

}
