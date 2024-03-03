package com.profitgym.profitgym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.PackageRepository;

import java.util.List;


@RestController
@RequestMapping("/admindashboard")
public class AdminController {

    @Autowired
    private PackageRepository packageRespository;

    @GetMapping("")
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
        System.out.println("viewPackages() method called");
        ModelAndView mav = new ModelAndView("packageAdminDash.html");
        List<Package> packages = this.packageRespository.findAll();
        mav.addObject("packages", packages);
        return mav;
    }

    @GetMapping("clientrequests")
    public ModelAndView viewRequests() {
        ModelAndView mav = new ModelAndView("clientReqAdminDash.html");
        return mav;
    }
    @GetMapping("memberships")
    public ModelAndView viewMemberships() {
        ModelAndView mav = new ModelAndView("membershipAdminDash.html");
        return mav;
    }
    @GetMapping("classes")
    public ModelAndView viewClasses() {
        ModelAndView mav = new ModelAndView("classAdminDash.html");
        return mav;
    }
    @GetMapping("addclient")
    public ModelAndView getClientForm() {
        ModelAndView mav = new ModelAndView("addClientAdminDash.html");
        return mav;
    }
    @GetMapping("addemployee")
    public ModelAndView getEmpForm() {
        ModelAndView mav = new ModelAndView("addEmpAdminDash.html");
        return mav;
    }
    @GetMapping("addclass")
    public ModelAndView getClassForm() {
        ModelAndView mav = new ModelAndView("addClassAdminDash.html");
        return mav;
    }
    @GetMapping("addpackage")
    public ModelAndView getPackageForm() {
        ModelAndView mav = new ModelAndView("addPackageAdminDash.html");
        mav.addObject("packageObj", new Package()); 
        return mav;
    }
    @PostMapping("addpackage")
    public String savePackage(@ModelAttribute Package packageObj) {
        this.packageRespository.save(packageObj);
        return "Added";
    }
    

}
