package com.profitgym.packagemicroservice.packagemicroservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.profitgym.packagemicroservice.packagemicroservices.models.Package;

import com.profitgym.packagemicroservice.packagemicroservices.repositories.PackageRepository;


import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admindashboard")
public class PackageController {

    @Autowired
    private PackageRepository packageRespository;

    @GetMapping("packages")
    public ResponseEntity getPackages() {
        List<Package> packages = this.packageRespository.findAll();
        return new ResponseEntity(packages , HttpStatus.OK);
    }
    
    @PostMapping("addpackage")
    public ResponseEntity addPackage(@RequestBody Package body) {

        Package myPackage = new Package();
        myPackage.setTitle(body.getTitle());
        myPackage.setNumOfMonths(body.getNumOfMonths());
        myPackage.setVisitsLimit(body.getVisitsLimit());
        myPackage.setFreezeLimit(body.getFreezeLimit());
        myPackage.setNumOfInvitations(body.getNumOfInvitations());
        myPackage.setNumOfInbodySessions(body.getNumOfInbodySessions());
        myPackage.setNumOfPrivateTrainingSessions(body.getNumOfPrivateTrainingSessions());
        myPackage.setPrice(body.getPrice());
        myPackage.setIsActivated(body.getIsActivated());

        this.packageRespository.save(myPackage);
        return new ResponseEntity(myPackage, HttpStatus.CREATED);
    }
    
    @GetMapping("package/{id}")
    public ResponseEntity<Package> getPackage(@PathVariable int id) {
        Package existingPackage = this.packageRespository.findById(id);
        return new ResponseEntity<>(existingPackage , HttpStatus.OK);
    }
    
}
