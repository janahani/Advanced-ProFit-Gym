package com.profitgym.profitgym.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/admindashboard")
public class AdminController {

    @GetMapping("main")
    public ModelAndView getAdminDash() {
        ModelAndView mav = new ModelAndView("adminDash.html");
        return mav;
    }

    @GetMapping("clients")
    public ModelAndView viewClients() {
        ModelAndView mav = new ModelAndView("clientAdminDash.html");
        return mav;
    }

    @GetMapping("employees")
    public ModelAndView viewEmployees() {
        ModelAndView mav = new ModelAndView("empAdminDash.html");
        return mav;
    }
    
    @GetMapping("packages")
    public ModelAndView viewPackages() {
        ModelAndView mav = new ModelAndView("packagesAdminDash.html");
        return mav;
    }

    @GetMapping("clientrequests")
    public ModelAndView viewRequests() {
        ModelAndView mav = new ModelAndView("clientReqAdminDash.html");
        return mav;
    }
    @GetMapping("addclient")
    public ModelAndView getClientForm() {
        ModelAndView mav = new ModelAndView("addClientAdminDash.html");
        return mav;
    }

}
