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
}
