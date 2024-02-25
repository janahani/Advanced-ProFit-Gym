package com.profitgym.profitgym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("profile")
    public ModelAndView getUserProfile() {
        ModelAndView mav = new ModelAndView("userprofile.html");
        return mav;
    }

    @GetMapping("bookpackage")
    public ModelAndView getPackageBooking() {
        ModelAndView mav = new ModelAndView("packagebooking.html");
        return mav;
    }

    @GetMapping("bookclass")
    public ModelAndView getClassBooking() {
        ModelAndView mav = new ModelAndView("classbooking.html");
        return mav;
    }
    
    @GetMapping("viewpackage")
    public ModelAndView viewPackage() {
        ModelAndView mav = new ModelAndView("viewpackage.html");
        return mav;
    }

    @GetMapping("viewclasses")
    public ModelAndView viewClasses() {
        ModelAndView mav = new ModelAndView("viewclasses.html");
        return mav;
    }

    @GetMapping("requestfreeze")
    public ModelAndView requestFreeze() {
        ModelAndView mav = new ModelAndView("requestfreeze.html");
        return mav;
    }
}
