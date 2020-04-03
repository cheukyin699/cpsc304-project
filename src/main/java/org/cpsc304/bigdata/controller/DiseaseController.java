package org.cpsc304.bigdata.controller;

import org.cpsc304.bigdata.db.dao.DiseaseDAO;
import org.cpsc304.bigdata.model.Diseases.Disease;
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
public class DiseaseController {

    @Autowired
    private DiseaseDAO diseaseDAO;
    private Logger logger = LoggerFactory.getLogger(DiseaseController.class);

    @PostMapping("/disease/{name}")
    public Disease getDisease(@PathVariable("name") final String name) {
        return null;
    }

    @DeleteMapping("/disease/{name}")
    public String deleteDisease(@PathVariable("name") final String name) {
        diseaseDAO.deleteDisease(name);
        return "ok";
    }

    @GetMapping("/disease")
    public List<Disease> searchDiseases(
            @RequestParam(value = "symptom", required = false) final String symptom,
            @RequestParam(value = "ct", required = false) final String trial) {
        if (symptom != null) {
            return diseaseDAO.findDiseaseBySymptom(symptom);
        }
        if (trial != null) {
            return diseaseDAO.findDiseaseByClinicalTrialName(trial);
        }

        return Collections.emptyList();
    }
}
