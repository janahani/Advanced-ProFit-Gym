package com.profitgym.profitgym.controllers;
import com.profitgym.profitgym.controllers.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.Client;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IndexController indexController;
    @GetMapping("/profile")
public ModelAndView getUserProfile(HttpSession session) {
    ModelAndView mav = new ModelAndView("userprofile.html");
    Client loggedInUser = (Client) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        mav.setViewName("redirect:/login");
    } else {
        mav.addObject("loggedInUser", loggedInUser);
    }
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

    @GetMapping("profsettings")
    public ModelAndView viewProfSettings() {
        ModelAndView mav = new ModelAndView("userprofsettings.html");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        session.invalidate();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

}
