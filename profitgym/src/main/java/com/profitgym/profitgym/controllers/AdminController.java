package com.profitgym.profitgym.controllers;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
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

import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.ClientRepository;
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
    private ClientRepository clientRepository;

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
        mav.addObject("clientObj", new Client());
        return mav;
    }

    @PostMapping("addclient")
    public ModelAndView saveClient(@ModelAttribute Client clientObj) {
        String generatedPassword = generateRandomPassword(8);
        clientObj.setPassword(generatedPassword);
        this.clientRepository.save(clientObj);
        sendEmail(clientObj.getEmail(), "Welcome to Our Profit Gym!",
                "Hello,\n\nYour account has been created. Your temporary password is: " + generatedPassword
                        + "\n\nPlease log in and change your password.");

        System.out.println("Client added");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admindashboard/addclient");
        return modelAndView;
    }

    @GetMapping("addemployee")
    public ModelAndView getEmpForm() {
        ModelAndView mav = new ModelAndView("addEmpAdminDash.html");
        mav.addObject("employeeObj", new Employee()); // Add employeeObj to the model
        mav.addObject("jobTitles", jobTitlesRepository.findAll());
        return mav;
    }

    @PostMapping("addemployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employeeObj,
            @RequestParam(value = "jobTitleHidden", required = false) Integer jobTitle) {
        ModelAndView modelAndView = new ModelAndView();
        if (jobTitle != null) {
            employeeObj.setJobTitle(jobTitle);
        }

        try {
            String generatedPassword = generateRandomPassword(8);
            String encoddedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(12));
            employeeObj.setPassword(encoddedPassword);
            this.employeeRepository.save(employeeObj);
            sendEmail(employeeObj.getEmail(), "Welcome to Our Company!",
                    "Hello,\n\nYour account has been created. Your temporary password is: " + generatedPassword
                            + "\n\nPlease log in and change your password.");

            System.out.println("Employee added");
            modelAndView.setViewName("redirect:/admindashboard/addemployee");
        } catch (Exception e) {
            System.out.println("error");
        }
        return modelAndView;
    }

    private void sendEmail(String recipientEmail, String subject, String generatedPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Welcome to Our Company!");
            message.setText(
                    "Hello,\n\nYour account has been created. Your temporary password is: " + generatedPassword +
                            "\n\nPlease log in and change your password.");

            emailSender.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    @PostMapping("deleteemployee")
    public ModelAndView deleteEmployee(@RequestParam("id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            employeeRepository.deleteById(employeeId);
            System.out.println("Employee deleted");
            modelAndView.setViewName("redirect:/admindashboard/deleteemployee");
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
        return modelAndView;
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
    public ModelAndView updateEmployee(@ModelAttribute Employee employeeObj,
            @RequestParam(value = "jobTitleHidden", required = false) Integer jobTitle) {
        // Log the ID of the employeeObj before saving
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("Employee ID before saving: " + employeeObj.getId());

        if (jobTitle != null) {
            employeeObj.setJobTitle(jobTitle);
        }
        try {
            this.employeeRepository.save(employeeObj);
            System.out.println("Employee updated successfully");
            modelAndView.setViewName("redirect:/admindashboard/editemployee");
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
        return modelAndView;
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
    public ModelAndView savePackage(@ModelAttribute Package packageObj) {
        this.packageRespository.save(packageObj);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admindashboard/addpackage");
        return modelAndView;
    }

}
