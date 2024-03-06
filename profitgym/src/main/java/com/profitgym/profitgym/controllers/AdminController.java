package com.profitgym.profitgym.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.JobTitlesRepository;
import com.profitgym.profitgym.repositories.PackageRepository;

import java.util.Random;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@RestController
@RequestMapping("/admindashboard")
public class AdminController {

    @Autowired
    private PackageRepository packageRespository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobTitlesRepository jobTitlesRepository;

     @Autowired
    private JavaMailSender emailSender;

    private static final String RANDOMCHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    private String generateRandomPassword(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(RANDOMCHAR_STRING.length());
            builder.append(RANDOMCHAR_STRING.charAt(index));
        }

        return builder.toString();
    }

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
        System.out.println("viewEmployees() method called");

        ModelAndView mav = new ModelAndView("empAdminDash.html");

        List<Employee> employees = this.employeeRepository.findAll();
        mav.addObject("employees", employees);

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

    @GetMapping("checkin")
    public ModelAndView viewCheckIn() {
        ModelAndView mav = new ModelAndView("checkinAdminDash.html");
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
        mav.addObject("employeeObj", new Employee()); // Add employeeObj to the model
        mav.addObject("jobTitles", jobTitlesRepository.findAll());
        return mav;
    }

    // @PostMapping("addemployee")
    // public String saveEmployee(@ModelAttribute Employee employeeObj,
    //         @RequestParam(value = "jobTitleHidden", required = false) Integer jobTitle) {
    //     if (jobTitle != null) {
    //         employeeObj.setJobTitle(jobTitle);
    //     }
    //     try {
    //         this.employeeRepository.save(employeeObj);
    //         System.out.println("employee added");
    //         return "added";
    //     } catch (Exception e) {
    //         return "error";
    //     }
    // }


    @PostMapping("addemployee")
    public String saveEmployee(@ModelAttribute Employee employeeObj,
        @RequestParam(value = "jobTitleHidden", required = false) Integer jobTitle) {
        if (jobTitle != null) {
            employeeObj.setJobTitle(jobTitle);
        }
    
        try {
            String generatedPassword = generateRandomPassword(8);
            employeeObj.setPassword(generatedPassword);
            this.employeeRepository.save(employeeObj);
            sendEmail(employeeObj.getEmail(), "Welcome to Our Company!", "Hello,\n\nYour account has been created. Your temporary password is: " + generatedPassword + "\n\nPlease log in and change your password.");
    
            System.out.println("Employee added");
            return "added";
        } catch (Exception e) {
            return "error";
        }
    }
    
    private void sendEmail(String recipientEmail, String subject, String generatedPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Welcome to Our Company!");
            message.setText("Hello,\n\nYour account has been created. Your temporary password is: " + generatedPassword +
             "\n\nPlease log in and change your password.");
            
            emailSender.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
    

    @GetMapping("editemployee")
    public ModelAndView editEmpForm(@RequestParam("id") int employeeId) {
        ModelAndView mav = new ModelAndView("editEmpAdminDash.html");
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            mav.addObject("employeeObj", employee); // Add employeeObj to the model
            mav.addObject("jobTitles", jobTitlesRepository.findAll());
        } else {
            mav.addObject("errorMessage", "Employee not found");
        }
        return mav;
    }
    
    
    @PostMapping("editemployee")
    public String updateEmployee(@ModelAttribute Employee employeeObj,
            @RequestParam(value = "jobTitleHidden", required = false) Integer jobTitle) {
        // Log the ID of the employeeObj before saving
        System.out.println("Employee ID before saving: " + employeeObj.getId());

        if (jobTitle != null) {
            employeeObj.setJobTitle(jobTitle);
        }
        try {
            this.employeeRepository.save(employeeObj);
            System.out.println("Employee updated successfully");
            return "Employee updated successfully";
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
            return "Error updating employee";
        }
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

    @SuppressWarnings("null")
    @PostMapping("addpackage")
    public String savePackage(@ModelAttribute Package packageObj) {
        this.packageRespository.save(packageObj);
        return "Added";
    }

}
