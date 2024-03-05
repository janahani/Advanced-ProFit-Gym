package com.profitgym.profitgym.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.Classes;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("")
public class IndexController {
    
    @GetMapping("/index")
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("index.html");
        return mav;
    }

    @GetMapping("/facilities")
    public ModelAndView getFacilities() {
        ModelAndView mav = new ModelAndView("facilities.html");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView mav = new ModelAndView("login.html");
        return mav;
    }

    @GetMapping("/contactus")
    public ModelAndView getContactUs() {
        ModelAndView mav = new ModelAndView("contactus.html");
        return mav;
    }

    @GetMapping("/register")
        public ModelAndView getSignUp() {
            ModelAndView mav = new ModelAndView("register.html");
            return mav;

    @GetMapping("/classes")
    public ModelAndView getClasses() {
        ModelAndView mav = new ModelAndView("classes.html");
        return mav;
    }

    @GetMapping("/memberships")
    public ModelAndView getMemberships() {
        ModelAndView mav = new ModelAndView("memberships.html");
        return mav;
    }

}
