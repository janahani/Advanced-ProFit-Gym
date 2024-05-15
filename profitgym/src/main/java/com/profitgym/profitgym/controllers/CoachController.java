package com.profitgym.profitgym.controllers;

import com.profitgym.profitgym.models.AssignedClass;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.ReservedClass;
import com.profitgym.profitgym.repositories.AssignedClassRepository;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.ReservedClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private AssignedClassRepository assignedClassRepository;

    @Autowired
    private ReservedClassRepository reservedClassRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/classes")
    public String viewAssignedClasses(HttpSession session, Model model) {
        // Check if coach is logged in
        Employee coach = (Employee) session.getAttribute("loggedInUser");
        if (coach == null || coach.getJobTitle() != 2) { // Assuming JobTitle 2 is for coaches
            return "redirect:/login"; 
        }

        // Fetch assigned classes for the coach
        int coachID = coach.getID();
        AssignedClass assignedClasses = assignedClassRepository.findByID(coachID);
        model.addAttribute("assignedClasses", assignedClasses);

        // Fetch reserved classes for the coach
        ReservedClass reservedClasses = reservedClassRepository.findByID(coachID);
        model.addAttribute("reservedClasses", reservedClasses);

        return "coach_classes"; // Assuming you have a Thymeleaf template named coach_classes.html
    }
}
