package com.profitgym.profitgym.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.profitgym.profitgym.models.Package;

import com.profitgym.profitgym.repositories.PackageRepository;


import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/admindashboard")
public class PackageController {

    @Autowired
    private PackageRepository packageRespository;

     @GetMapping("addpackage")
    public ModelAndView getPackageForm() {
        ModelAndView mav = new ModelAndView("addPackageAdminDash.html");
        mav.addObject("packageObj", new Package());
        return mav;
    }

    @SuppressWarnings("null")
    @PostMapping("addpackage")
    public ModelAndView savePackage(@ModelAttribute Package packageObj) {
        this.packageRespository.save(packageObj);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admindashboard/addpackage");
        return modelAndView;
    }

    @GetMapping("packages")
    public ModelAndView viewPackages() {
        System.out.println("viewPackages() method called");
        ModelAndView mav = new ModelAndView("packageAdminDash.html");
        List<Package> packages = this.packageRespository.findAll();
        mav.addObject("packages", packages);
        return mav;
    }

    @PostMapping("/package-activation")
    public ModelAndView handlePackageActivation(@RequestParam("id") int id,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Package existingPackage = this.packageRespository.findById(id);

            if ("activate".equals(request.getParameter("action"))) {
                existingPackage.setIsActivated("Activated");
            } else {
                existingPackage.setIsActivated("Not Activated");
            }

            this.packageRespository.save(existingPackage);

            modelAndView.setViewName("redirect:/admindashboard/packages");
        } catch (Exception e) {
            modelAndView.setViewName("error_page");
            System.out.println("Error handling package activation: " + e.getMessage());
        }

        return modelAndView;
    }

   
    
}
