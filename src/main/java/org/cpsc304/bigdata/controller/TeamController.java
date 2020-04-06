package org.cpsc304.bigdata.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/team")
public class TeamController {

    private Logger logger = LoggerFactory.getLogger(TeamController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {return "team";}
}
