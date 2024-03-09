package com.profitgym.profitgym.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.profitgym.profitgym.models.Classes;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.PackageRepository;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("")
public class IndexController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PackageRepository packageRepository;

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
        Client newClient = new Client();
        mav.addObject("client", newClient);
        return mav;
    }

    @PostMapping("/login")
    public RedirectView loginProcess(@RequestParam("email") String email, 
                                 @RequestParam("Password") String password,
                                 HttpSession session) {
    Client dbClient = this.clientRepository.findByEmail(email);
    if (dbClient != null) {
        Boolean isPasswordMatched = BCrypt.checkpw(password, dbClient.getPassword());
        if (isPasswordMatched) {
            session.setAttribute("loggedInUser", dbClient);
            return new RedirectView("/user/profile");
        } else {
            return new RedirectView("/login?error=wrongPassword");
        }
    } else {
        return new RedirectView("/login?error=userNotFound");

    }
    }
    @GetMapping("contactus")
    public ModelAndView getContactUs() {
        ModelAndView mav = new ModelAndView("contactus.html");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView addClient() {
        ModelAndView mav = new ModelAndView("register.html");
        Client newClient = new Client();
        mav.addObject("client", newClient);
        return mav;
    }

    @PostMapping("register")
    public ModelAndView saveClient(@ModelAttribute Client client) {
        String encoddedPassword = BCrypt.hashpw(client.getPassword(), BCrypt.gensalt(12));
        client.setPassword(encoddedPassword);
        this.clientRepository.save(client);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
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
        List<Package> packages = this.packageRepository.findAll();
        mav.addObject("packages", packages);
        return mav;
    }
}
