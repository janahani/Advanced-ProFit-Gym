package com.profitgym.profitgym.controllers;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties.Packages;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.AssignedClass;
import com.profitgym.profitgym.models.ClassDays;
import com.profitgym.profitgym.models.Classes;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.AssignedClassRepository;
import com.profitgym.profitgym.repositories.ClassDaysRepository;
import com.profitgym.profitgym.repositories.ClassesRepository;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.JobTitlesRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.PackageRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Random;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    private ClassesRepository classesRepository;

    @Autowired
    private AssignedClassRepository assignedClassRepository;

    @Autowired
    private ClassDaysRepository classDaysRepository;

    @Autowired
    private MembershipsRepository membershipsRepository;

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
        int jobTitleValue = 3;
        ModelAndView mav = new ModelAndView("adminDash.html");

        List<Client> recentClients = this.clientRepository.findTop5ByOrderByCreatedAtDesc();
        mav.addObject("recentClients", recentClients);

        long packagesCount = this.packageRespository.count();
        long classesCount = this.classesRepository.count();
        long employeesCount = this.employeeRepository.count();
        long coachesCount = this.employeeRepository.countByJobTitle(jobTitleValue);

        mav.addObject("packagesCount", packagesCount);
        mav.addObject("classesCount", classesCount);
        mav.addObject("employeesCount", employeesCount);
        mav.addObject("coachesCount", coachesCount);

        return mav;
    }

    @GetMapping("clients")
    public ModelAndView viewClients() {
        System.out.println("viewClients() method called");

        ModelAndView mav = new ModelAndView("clientAdminDash.html");
        List<Client> clients = this.clientRepository.findAll();
        mav.addObject("clients", clients);

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

    @GetMapping("clientrequests")
    public ModelAndView viewRequests() {
        ModelAndView mav = new ModelAndView("clientReqAdminDash.html");
        return mav;
    }

    @GetMapping("memberships")
    public ModelAndView viewMemberships() {
        ModelAndView mav = new ModelAndView("membershipAdminDash.html");
        List<Memberships> memberships = membershipsRepository.findAll();
        List<Client> clients = new ArrayList<>();
        List<Package> packages = new ArrayList<>();

        if(memberships!=null)
        {
            for (Memberships membership : memberships) {
                
                Client client = clientRepository.findById(membership.getClientID()).orElse(null);
                clients.add(client);
                Package package1 = packageRespository.findById(membership.getPackageID());
                if(packages.contains(package1)==false)
                {
                     packages.add(package1);
                }
            }
        }
        mav.addObject("memberships", memberships);
        mav.addObject("clients", clients);
        mav.addObject("packages", packages);
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
        List<AssignedClass> assignedClasses = this.assignedClassRepository.findAll();
        List<Classes> classes = new ArrayList<>();
        for (AssignedClass assignedClass : assignedClasses) {
            int classId = assignedClass.getClassID();
            Classes classInformation = this.classesRepository.findByID(classId);
            classes.add(classInformation);
        }
        mav.addObject("assignedClasses", assignedClasses);
        mav.addObject("classes", classes);
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
        String encoddedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(12));
        clientObj.setPassword(encoddedPassword);
        this.clientRepository.save(clientObj);
        sendEmail(clientObj.getEmail(), "Welcome to Profit Gym!",
                "Hello, " + clientObj.getFirstName()
                        + ". \n\nYour account has been created. Your temporary password is: " + generatedPassword
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
            String emailBody = "Hello, " + employeeObj.getName()
                    + ". \n\nYour account has been created. Your temporary password is: " + generatedPassword
                    + "\n\nPlease log in and change your password.";
            sendEmail(employeeObj.getEmail(), "Welcome to Our Company!", emailBody);

            System.out.println("Employee added");
            modelAndView.setViewName("redirect:/admindashboard/addemployee");
        } catch (Exception e) {
            System.out.println("error");
        }
        return modelAndView;
    }

    private void sendEmail(String recipientEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject(subject);
            message.setText(body);

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
            modelAndView.setViewName("redirect:/admindashboard/employees");
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("editclient")
    public ModelAndView editClientForm(@RequestParam("id") int clientId) {
        ModelAndView mav = new ModelAndView("editClientAdminDash.html");
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()) {
            Client clientObj = clientOptional.get();
            mav.addObject("clientObj", clientObj);

        } else {
            mav.addObject("errorMessage", "Client not found");
        }
        return mav;
    }

    @PostMapping("editclient")
    public ModelAndView updateClient(@ModelAttribute Client clientObj, @RequestParam("id") int clientId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Optional<Client> existingClientOptional = clientRepository.findById(clientId);
            if (existingClientOptional.isPresent()) {
                Client existingClient = existingClientOptional.get();

                // Check for null before updating each property
                if (clientObj.getFirstName() != null) {
                    existingClient.setFirstName(clientObj.getFirstName());
                }
                if (clientObj.getLastName() != null) {
                    existingClient.setLastName(clientObj.getLastName());
                }
                if (clientObj.getAge() != 0) {
                    existingClient.setAge(clientObj.getAge());
                }
                if (clientObj.getGender() != null) {
                    existingClient.setGender(clientObj.getGender());
                }
                if (clientObj.getWeight() != 0) {
                    existingClient.setWeight(clientObj.getWeight());
                }
                if (clientObj.getHeight() != 0) {
                    existingClient.setHeight(clientObj.getHeight());
                }
                if (clientObj.getEmail() != null) {
                    existingClient.setEmail(clientObj.getEmail());
                }
                if (clientObj.getPassword() != null) {
                    existingClient.setPassword(clientObj.getPassword());
                }
                if (clientObj.getPhoneNumber() != null) {
                    existingClient.setPhoneNumber(clientObj.getPhoneNumber());
                }

                // Save the updated client
                clientRepository.save(existingClient);

                System.out.println("Client updated successfully");
                modelAndView.setViewName("redirect:/admindashboard/clients");
            } else {
                System.out.println("Error updating client: Client not found");
                modelAndView.setViewName("redirect:/admindashboard/clients");
            }
        } catch (Exception e) {
            System.out.println("Error updating client: " + e.getMessage());
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
        System.out.println("Employee ID before saving: " + employeeObj.getID());

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
        mav.addObject("classObj", new Classes());
        return mav;
    }

    @PostMapping("/addclass")
    public ModelAndView addClass(@ModelAttribute("classObj") @Valid Classes classObj,
            @RequestParam("week-days[]") List<String> weekDays,
            @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            // Handle file upload and get file path
            String fileName = handleFileUpload(file);

            if (fileName != null) {
                classObj.setImgPath(fileName);
            }

            this.classesRepository.save(classObj);

            // Save the selected weekdays
            for (String day : weekDays) {
                ClassDays classDay = new ClassDays();
                classDay.setClassID(classObj.getID());
                classDay.setDays(day);
                this.classDaysRepository.save(classDay);
            }

            // Redirect to a success page
            modelAndView.setViewName("redirect:/admindashboard/classes");
        } catch (Exception e) {
            System.out.println("Error adding class: " + e.getMessage());
            modelAndView.setViewName("error_page");
        }

        return modelAndView;
    }

    private String handleFileUpload(MultipartFile file) {
        String filePath = null;
        String fileName = null;
        try {
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/profitgym/src/main/resources/static/images/";
                filePath = uploadDir + fileName;

                // Save the uploaded file to the desired location
                File destFile = new File(filePath);
                file.transferTo(destFile);
            }
        } catch (Exception e) {
            System.out.println("Error storing file: " + e.getMessage());
        }
        return fileName;
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

    @GetMapping("assignclass")
    public ModelAndView getClasses() {
        ModelAndView mav = new ModelAndView("assignClassAdminDash.html");
        List<Classes> classes = this.classesRepository.findAll();
        mav.addObject("classes", classes);
        List<Employee> coaches = this.employeeRepository.findByJobTitle(3);
        mav.addObject("coaches", coaches);

        mav.addObject("assignClassObj", new AssignedClass());

        LocalDate currentDate = LocalDate.now();

        List<String> availableClassDays = new ArrayList<>();

        // Iterate through the classes to find the upcoming class days
        for (Classes classObj : classes) {
            int classId = classObj.getID();
            List<ClassDays> classDays = this.classDaysRepository.findByClassID(classId);

            Map<String, DayOfWeek> dayNameToDayOfWeek = new HashMap<>();
            dayNameToDayOfWeek.put("Monday", DayOfWeek.MONDAY);
            dayNameToDayOfWeek.put("Tuesday", DayOfWeek.TUESDAY);
            dayNameToDayOfWeek.put("Wednesday", DayOfWeek.WEDNESDAY);
            dayNameToDayOfWeek.put("Thursday", DayOfWeek.THURSDAY);
            dayNameToDayOfWeek.put("Friday", DayOfWeek.FRIDAY);
            dayNameToDayOfWeek.put("Saturday", DayOfWeek.SATURDAY);
            dayNameToDayOfWeek.put("Sunday", DayOfWeek.SUNDAY);

            // Iterate through the class days to find the upcoming dates
            for (ClassDays day : classDays) {
                String dayOfWeekStr = day.getDays();
                DayOfWeek dayOfWeek = dayNameToDayOfWeek.get(dayOfWeekStr);
                LocalDate upcomingDate = currentDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));
                String formattedDate = upcomingDate.format(DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy"));
                availableClassDays.add(formattedDate);
            }
        }

        mav.addObject("availableClassDays", availableClassDays);

        return mav;
    }

    @PostMapping("assignclass")
    public ModelAndView assignClass(@ModelAttribute("assignClassObj") AssignedClass assignedClass,
            @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,
            @RequestParam("ClassDay") String classDay) {

        ModelAndView modelAndView = new ModelAndView();
        try {
            assignedClass.setClassID(assignedClass.getClassID());
            assignedClass.setCoachID(assignedClass.getCoachID());

            assignedClass.setAvailablePlaces(assignedClass.getNumOfAttendants());

            assignedClass.setStartTime(startTime);
            assignedClass.setEndTime(endTime);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MM-yyyy", Locale.ENGLISH);
            LocalDate selectedDate = LocalDate.parse(classDay, formatter);

            assignedClass.setDate(selectedDate);

            this.assignedClassRepository.save(assignedClass);

            modelAndView.setViewName("redirect:/admindashboard/assignclass");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            modelAndView.setViewName("error_page");
        }
        return modelAndView;
    }

}
