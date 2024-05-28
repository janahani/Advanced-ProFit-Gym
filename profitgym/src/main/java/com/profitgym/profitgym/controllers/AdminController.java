package com.profitgym.profitgym.controllers;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitgym.profitgym.models.AssignedClass;
import com.profitgym.profitgym.models.ClassDays;
import com.profitgym.profitgym.models.Classes;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.models.ReservedClass;
import com.profitgym.profitgym.models.ServiceResponse;
import com.profitgym.profitgym.repositories.AssignedClassRepository;
import com.profitgym.profitgym.repositories.ClassDaysRepository;
import com.profitgym.profitgym.repositories.ClassesRepository;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.JobTitlesRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.services.PackageService;
import com.profitgym.profitgym.repositories.ReservedClassRepository;

import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admindashboard")
public class AdminController {

    @Autowired
    private PackageService packageService;

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
    private ReservedClassRepository reservedClassRepository;

    @Autowired
    private ClassDaysRepository classDaysRepository;

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private JavaMailSender emailSender;

    private static final String RANDOMCHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public AdminController()
    {

    }

    public AdminController(ClientRepository clientRepository,JavaMailSender emailSender)
    {
        this.clientRepository=clientRepository;
        this.emailSender = emailSender;
    }


    public AdminController(EmployeeRepository employeeRepository,JavaMailSender emailSender)
    {
        this.employeeRepository=employeeRepository;
        this.emailSender = emailSender;
    }

    private String generateRandomPassword(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(RANDOMCHAR_STRING.length());
            builder.append(RANDOMCHAR_STRING.charAt(index));
        }

        return builder.toString();
    }

    private void saveUpdatedFieldsForEmp(Employee employeeObj, Employee employee) {
        if (employeeObj.getJobTitle() != 0) {
            employee.setJobTitle(employeeObj.getJobTitle());
        }
        if (employeeObj.getSalary() != 0) {
            employee.setSalary(employeeObj.getSalary());
        }
        if (employeeObj.getPhoneNumber() != 0) {
            employee.setPhoneNumber(employeeObj.getPhoneNumber());
        }
        if (employeeObj.getJobTitle() != 0) {
            employee.setJobTitle(employeeObj.getJobTitle());
        }
        if (employeeObj.getName() != null) {
            employee.setName(employeeObj.getName());
        }
        if (employeeObj.getEmail() != null) {
            employee.setEmail(employeeObj.getEmail());
        }
        if (employeeObj.getPhoneNumber() != 0) {
            employee.setPhoneNumber(employeeObj.getPhoneNumber());
        }
        if (employeeObj.getPassword() != null) {
            String encoddedPassword = BCrypt.hashpw(employeeObj.getPassword(), BCrypt.gensalt(12));
            employee.setPassword(encoddedPassword);
        }
    }

    private void saveUpdatedFieldsForClient(Client clientObj, Client existingClient) {
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
            String encoddedPassword = BCrypt.hashpw(clientObj.getPassword(), BCrypt.gensalt(12));
            existingClient.setPassword(encoddedPassword);
        }
        if (clientObj.getPhoneNumber() != null) {
            existingClient.setPhoneNumber(clientObj.getPhoneNumber());
        }

    }

    @GetMapping("")
    public ModelAndView getAdminDash() {
        int jobTitleValue = 3;
        ModelAndView mav = new ModelAndView("adminDash.html");

        List<Client> recentClients = this.clientRepository.findTop5ByOrderByCreatedAtDesc();
        mav.addObject("recentClients", recentClients);
        List<Package> packages = this.packageService.findAll();
        long packagesCount = packages.size();
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
        ModelAndView mav = new ModelAndView("clientAdminDash.html");
        List<Client> clients = this.clientRepository.findAll();
        List<Boolean> hasActiveMembership = new ArrayList<>();
        for (Client client : clients) {
            Memberships membership = this.membershipsRepository.findByClientID(client.getID());
            boolean isActiveMember = false;
            if (membership != null && membership.getIsActivated().equals("Activated")) {
                isActiveMember = true;
            }
            hasActiveMembership.add(isActiveMember);
        }
        mav.addObject("clients", clients);
        mav.addObject("hasActiveMembership", hasActiveMembership);
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

    @GetMapping("clientrequests")
    public ModelAndView viewRequests() {
        ModelAndView mav = new ModelAndView("clientReqAdminDash.html");
        List<Memberships> memberships = membershipsRepository.findByIsActivated("Pending");
        List<Client> clients = new ArrayList<>();
        List<Package> packages = new ArrayList<>();
        if (memberships != null) {
            for (Memberships membership : memberships) {

                Client client = clientRepository.findById(membership.getClientID());
                if (clients.contains(client) == false) {
                    clients.add(client);
                }
                Package package1 = packageService.findById(membership.getPackageID());
                if (packages.contains(package1) == false) {
                    packages.add(package1);
                }
            }
        }

        mav.addObject("memberships", memberships);
        mav.addObject("packages", packages);
        mav.addObject("clients", clients);

        List<ReservedClass> reservedClassesList = this.reservedClassRepository.findByIsActivated("Pending");
        List<Classes> classesList = new ArrayList<>();
        List<Client> clientsList = new ArrayList<>();
        List<Employee> coachesList = new ArrayList<>();
        List<AssignedClass> assignedClassesList = new ArrayList<>();
        for (ReservedClass reservedClass : reservedClassesList) {
            int assignedClassId = reservedClass.getAssignedClassID();
            int clientId = reservedClass.getClientID();
            int coachId = reservedClass.getCoachID();

            AssignedClass assignedClassInfo = this.assignedClassRepository.findByID(assignedClassId);
            if (assignedClassesList.contains(assignedClassInfo) == false) {
                assignedClassesList.add(assignedClassInfo);

                int classId = assignedClassInfo.getClassID();
                Classes classInfo = this.classesRepository.findByID(classId);
                if (classesList.contains(classInfo) == false) {
                    classesList.add(classInfo);
                }
            } else {
                System.out.println("error");
            }
            Client clientInfo = this.clientRepository.findById(clientId);
            if (clientsList.contains(clientInfo) == false) {
                clientsList.add(clientInfo);
            }

            Employee coachInfo = this.employeeRepository.findByID(coachId);
            if (coachesList.contains(coachInfo) == false) {
                coachesList.add(coachInfo);
            }

        }
        mav.addObject("reservedClassesList", reservedClassesList);
        mav.addObject("classesList", classesList);
        mav.addObject("clientsList", clientsList);
        mav.addObject("coachesList", coachesList);
        mav.addObject("assignedClassesList", assignedClassesList);

        return mav;
    }

    @PostMapping("/acceptReservedClass")
    public ModelAndView acceptReservedClass(@RequestParam("reservedClassId") int reservedClassId) {
        ReservedClass reservedClass = this.reservedClassRepository.findByID(reservedClassId);
        AssignedClass assignedClass = this.assignedClassRepository.findByID(reservedClass.getAssignedClassID());

        if (reservedClass != null && assignedClass.getAvailablePlaces() > 0) {
            reservedClass.setIsActivated("Activated");
            int availablePlaces = assignedClass.getAvailablePlaces();
            availablePlaces = availablePlaces - 1;
            assignedClass.setAvailablePlaces(availablePlaces);
            this.assignedClassRepository.save(assignedClass);
            reservedClassRepository.save(reservedClass);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
    }

    @PostMapping("/declineReservedClass")
    public ModelAndView declineReservedClass(@RequestParam("reservedClassId") int reservedClassId) {
        ReservedClass reservedClass = this.reservedClassRepository.findByID(reservedClassId);
        if (reservedClass != null) {
            reservedClass.setIsActivated("Not Activated");
            reservedClassRepository.save(reservedClass);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
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
        ModelAndView modelAndView = new ModelAndView();

        Client existingClient = clientRepository.findByEmail(clientObj.getEmail());
        if (existingClient != null) {
            modelAndView.setViewName("redirect:/admindashboard/addclient?EmailAlreadyExists");
            return modelAndView;
        }

        try {
            String generatedPassword = generateRandomPassword(8);
            String encodedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(12));
            clientObj.setPassword(encodedPassword);
            this.clientRepository.save(clientObj);
            String emailBody = "Hello, " + clientObj.getFirstName()
                    + ". \n\nYour account has been created. Your temporary password is: " + generatedPassword
                    + "\n\nPlease log in and change your password.";
            sendEmail(clientObj.getEmail(), "Welcome to Profit Gym!", emailBody);

            System.out.println("Client added");
            modelAndView.setViewName("redirect:/admindashboard/addclient");
        } catch (Exception e) {
            System.out.println("Error adding client: " + e.getMessage());
            modelAndView.setViewName("error_page");
        }
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

        Employee existingEmployee = this.employeeRepository.findByEmail(employeeObj.getEmail());
        if (existingEmployee != null) {
            modelAndView.setViewName("redirect:/admindashboard/addemployee?EmailAlreadyExists");
            return modelAndView;
        }

        try {
            String generatedPassword = generateRandomPassword(8);
            String encodedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(12));
            employeeObj.setPassword(encodedPassword);
            this.employeeRepository.save(employeeObj);

            String emailBody = "Hello, " + employeeObj.getName()
                    + ". \n\nYour account has been created. Your temporary password is: " + generatedPassword
                    + "\n\nPlease log in and change your password.";
            sendEmail(employeeObj.getEmail(), "Welcome to Our Company!", emailBody);

            System.out.println("Employee added");
            modelAndView.setViewName("redirect:/admindashboard/addemployee");
        } catch (Exception e) {
            System.out.println("Error adding class: " + e.getMessage());
            modelAndView.setViewName("error_page");
        }
        return modelAndView;
    }

    public void sendEmail(String recipientEmail, String subject, String body) {
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

    @PostMapping("/deleteemployee")
    public ModelAndView deleteEmployee(@RequestParam("id") int employeeId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            employeeRepository.deleteById(employeeId);
            System.out.println("Employee deleted");
            // Redirect to the employees page after successful deletion
            modelAndView.setViewName("redirect:/admindashboard/employees");
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            // Redirect to an error page or display an error message
            modelAndView.setViewName("error");
            modelAndView.addObject("message", "Error deleting employee: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("editclient")
    public ModelAndView editClientForm(@RequestParam("id") int clientId) {
        ModelAndView mav = new ModelAndView("editClientAdminDash.html");
        Client clientObj = clientRepository.findById(clientId);
        if (clientObj != null) {
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
            Client existingClient = clientRepository.findById(clientId);
            if (existingClient != null) {
                saveUpdatedFieldsForClient(clientObj, existingClient);
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
        Employee optionalEmployee = this.employeeRepository.findByID(employeeId);
        if (optionalEmployee != null) {
            mav.addObject("employeeObj", optionalEmployee); // Add employeeObj to the model
            mav.addObject("jobTitles", jobTitlesRepository.findAll());
        } else {
            mav.setViewName("redirect:/admindashboard/employees");
        }
        return mav;
    }

    @PostMapping("editemployee")
    public ResponseEntity<Object> updateEmployee(@RequestBody String employeeJson)
            throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employeeObj = objectMapper.readValue(employeeJson, Employee.class);

        String status;
        Employee employee = this.employeeRepository.findByID(employeeObj.getID());
        System.out.println(employeeObj);
        saveUpdatedFieldsForEmp(employeeObj, employee);
        ServiceResponse<String> response;
        try {
            this.employeeRepository.save(employee);
            status = "Employee updated successfully";
            response = new ServiceResponse<String>("success", status);
            System.out.println("Employee updated successfully");
        } catch (Exception e) {
            status = "Error updating employee: " + e.getMessage();
            System.out.println("Error updating employee: " + e.getMessage());
            response = new ServiceResponse<String>("error", status);
        }

        return new ResponseEntity<Object>(response, HttpStatus.OK);
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

    @GetMapping("assignclass")
    public ModelAndView getClasses() {
        ModelAndView mav = new ModelAndView("assignClassAdminDash.html");
        List<Classes> classes = this.classesRepository.findAll();
        mav.addObject("classes", classes);
        List<Employee> coaches = this.employeeRepository.findByJobTitle(3);
        mav.addObject("coaches", coaches);
        mav.addObject("assignClassObj", new AssignedClass());
        return mav;
    }

    @GetMapping("/assignclass/classdays/{classId}")
    @ResponseBody
    public List<String> getClassDays(@PathVariable("classId") int classId) {
        LocalDate currentDate = LocalDate.now();
        List<String> availableClassDays = new ArrayList<>();

        // Classes classObj = this.classesRepository.findById(classId);
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

        return availableClassDays;
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

    @GetMapping("/viewAssignedClasses")
    public ModelAndView viewAssignedClasses(HttpSession session) {
        ModelAndView mav = new ModelAndView("coach_classes.html");
        Employee coach = (Employee) session.getAttribute("loggedInEmp");
        if (coach == null || coach.getJobTitle() != 3) {
            mav.setViewName("/loginemployee");
        }

        int coachID = coach.getID();
        List<AssignedClass> assignedClasses = assignedClassRepository.findByCoachID(coachID);
        List<Classes> classes = new ArrayList<>();
        if (assignedClasses != null) {
            for (AssignedClass assignedClass : assignedClasses) {
                Classes classObj = this.classesRepository.findByID(assignedClass.getClassID());
                if (classes.contains(classObj) == false) {
                    classes.add(classObj);
                }
            }
            mav.addObject("assignedClasses", assignedClasses);
            mav.addObject("classes", classes);
        }
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        session.invalidate();
        modelAndView.setViewName("redirect:/loginemployee");
        return modelAndView;
    }

    @GetMapping("profilesettings")
    public ModelAndView viewProfSettings(HttpSession session) {
        ModelAndView mav = new ModelAndView("employeeProfileSettings.html");
        Employee sessionEmployee = (Employee) session.getAttribute("loggedInEmp");
        mav.addObject("employeeObj", sessionEmployee);
        return mav;
    }

    @PostMapping("/profilesettings")
    public ModelAndView updateEmployeeProfile(@Valid @ModelAttribute Employee empObj, BindingResult bindingResult,
            HttpSession session, @RequestParam("action") String action) {
        Employee sessionEmployee = (Employee) session.getAttribute("loggedInEmp");

        ModelAndView modelAndView = new ModelAndView();

        if (action.equals("updateProfile")) {
            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("employeeProfileSettings.html");
                return modelAndView;
            }

            sessionEmployee.setName(empObj.getName());
            sessionEmployee.setEmail(empObj.getEmail());
            sessionEmployee.setPhoneNumber(empObj.getPhoneNumber());
            sessionEmployee.setAddress(empObj.getAddress());

            session.setAttribute("loggedInEmp", sessionEmployee);

            modelAndView.setViewName("/admindashboard/employees");
            return modelAndView;
        } else {
            modelAndView.setViewName("errorPage");
            modelAndView.addObject("errorMessage", "Invalid action specified.");
            return modelAndView;
        }
    }

}
