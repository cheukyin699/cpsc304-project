package org.cpsc304.bigdata.controller;

import org.cpsc304.bigdata.db.dao.DiseaseDAO;
import org.cpsc304.bigdata.model.Diseases.Disease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class DiseaseController {

    @Autowired
    private DiseaseDAO diseaseDAO;

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
    public List<Disease> searchDiseases(@RequestParam("symptom") final String symptom) {
        return diseaseDAO.findDiseaseBySymptom(symptom);
    }
}
