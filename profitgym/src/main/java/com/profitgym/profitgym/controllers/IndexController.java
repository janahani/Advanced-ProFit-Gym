package com.profitgym.profitgym.controllers;

import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.profitgym.profitgym.models.Classes;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.repositories.ClientRepository;


import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("")
public class IndexController {

    @Autowired
    private ClientRepository clientRespository;

    @GetMapping("/index")
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("index.html");
        return mav;
    }

    @GetMapping("facilities")
    public ModelAndView getFacilities() {
        ModelAndView mav = new ModelAndView("facilities.html");
        return mav;
    }

    @GetMapping("login")
    public ModelAndView getLogin() {
        ModelAndView mav = new ModelAndView("login.html");
        return mav;
    }

    @GetMapping("contactus")
    public ModelAndView getContactUs() {
        ModelAndView mav = new ModelAndView("contactus.html");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView addClient() {
        ModelAndView mav = new ModelAndView("register.html");
        Client newClient= new Client();
        mav.addObject("client", newClient);
        return mav;
    }
    
    @PostMapping("register")
    public String saveClient(@ModelAttribute Client client) {
        String encoddedPassword=BCrypt.hashpw(client.getPassword(),BCrypt.gensalt(12));
        client.setPassword(encoddedPassword);
        this.clientRespository.save(client);
        return "Added";
    }

        }

    @GetMapping("classes")
    public ModelAndView getClasses() {
        ModelAndView mav = new ModelAndView("classes.html");
        return mav;
    }

    @GetMapping("memberships")
    public ModelAndView getMemberships() {
        System.out.println("viewPackages() method called");
        ModelAndView mav = new ModelAndView("memberships.html");
        List<Package> packages = this.packageRespository.findAll();
        mav.addObject("packages", packages);
        return mav;
    }
}
