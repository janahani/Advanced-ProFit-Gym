package com.profitgym.profitgym.controllers;

import com.profitgym.profitgym.controllers.IndexController;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.annotation.Validated;

import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.PackageRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    IndexController indexController;

    public UserController(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

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
        System.out.println("viewPackages() method called");
        ModelAndView mav = new ModelAndView("packagebooking.html");
        List<Package> packages = this.packageRepository.findAll();
        mav.addObject("packages", packages);
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


    @PostMapping("/profsettings")
    public ModelAndView updateClient(@Valid @ModelAttribute Client clientObj,BindingResult bindingResult,HttpSession session,@RequestParam("action") String action) {
        Client sessionClient = (Client) session.getAttribute("loggedInUser");
        ModelAndView modelAndView = new ModelAndView();

        if ("update".equals(action)) {
            sessionClient.setFirstName(clientObj.getFirstName());
            sessionClient.setLastName(clientObj.getLastName());
            sessionClient.setPhoneNumber(clientObj.getPhoneNumber());
            sessionClient.setEmail(clientObj.getEmail());

            if (clientObj.getPassword() != null && !clientObj.getPassword().isEmpty()) {
                String encodedPassword = BCrypt.hashpw(clientObj.getPassword(), BCrypt.gensalt(12));
                sessionClient.setPassword(encodedPassword);
            }

            try {
                this.clientRepository.save(sessionClient);
                session.setAttribute("loggedInUser", sessionClient);
                System.out.println("Client updated successfully");
                modelAndView.setViewName("redirect:/user/profsettings");
            } catch (Exception e) {
                System.out.println("Error updating client: " + e.getMessage());
            }
        } else if ("delete".equals(action)) {
            try {
                System.out.println(sessionClient.getID());
                this.clientRepository.deleteById(sessionClient.getID());
                session.invalidate();
                System.out.println("Client deleted");
                modelAndView.setViewName("redirect:/index");
            } catch (Exception e) {
                System.out.println("Error deleting client: " + e.getMessage());
            }
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        session.invalidate();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

}
