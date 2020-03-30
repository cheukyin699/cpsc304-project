package org.cpsc304.bigdata.controller;

import org.cpsc304.bigdata.db.DatabaseConnectionHandler;
import org.cpsc304.bigdata.db.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class TestController {

    @Autowired          // Auto-initialize this variable
    private DatabaseConnectionHandler dbHandler;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap map) {
//        map.addAttribute("version", dbHandler.getVersion());
        map.addAttribute("user", userDAO.findUserFromUsername("hbtaussig"));
        return "index";
    }
}
