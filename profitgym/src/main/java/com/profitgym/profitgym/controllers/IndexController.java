package com.profitgym.profitgym.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("")
public class IndexController {
    
    @GetMapping("/index")
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("index.html");
        return mav;
    }

    @GetMapping("/facilties")
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


}
