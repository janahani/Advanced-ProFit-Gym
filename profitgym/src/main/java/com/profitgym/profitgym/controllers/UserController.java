package com.profitgym.profitgym.controllers;

import com.profitgym.profitgym.controllers.IndexController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.annotation.Validated;

import com.profitgym.profitgym.models.AssignedClass;
import com.profitgym.profitgym.models.Classes;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.models.ReservedClass;
import com.profitgym.profitgym.models.ScheduledUnfreeze;
import com.profitgym.profitgym.repositories.AssignedClassRepository;
import com.profitgym.profitgym.repositories.ClassesRepository;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.PackageRepository;
import com.profitgym.profitgym.repositories.ReservedClassRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.ScheduledUnfreezeRepository;

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

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private AssignedClassRepository assignedClassRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private ReservedClassRepository reservedClassRepository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public UserController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public int calculateFreezeDuration(LocalDate currentDate, LocalDate freezeEndDate) {
        int freezeDuration = (int) ChronoUnit.DAYS.between(currentDate, freezeEndDate);
        return freezeDuration;
    }

    public void updateMembershipEndDate(Memberships membership, int freezeDuration) {
        LocalDate membershipEndDate = membership.getEndDate().plusDays(freezeDuration);
        int newFreezeCount = membership.getFreezeCount() - freezeDuration;
        membership.setFreezeCount(newFreezeCount);
        membership.setEndDate(membershipEndDate);
        membership.setFreezed("Freezed");
        this.membershipsRepository.save(membership);
    }

    public void createScheduledUnfreeze(int membershipID, LocalDate currentDate, LocalDate freezeEnddate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEnddate);
        scheduledUnfreeze.setMembershipID(membershipID);
        this.scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    @GetMapping("/profile")
    public ModelAndView getUserProfile(HttpSession session) {
        ModelAndView mav = new ModelAndView("userprofile.html");
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            mav.setViewName("redirect:/login");
        } else {
            Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());
            Package pack = null;
            LocalDate now = LocalDate.now();
            if (membership != null && membership.getIsActivated()=="Activated" && membership.getEndDate().isAfter(now)) {
                pack = packageRepository.findById(membership.getPackageID());
                mav.addObject("package", pack);
                mav.addObject("membership", membership);
            }
        }
        mav.addObject("loggedInUser", loggedInUser);
        return mav;
    }

    @GetMapping("bookpackage")
    public ModelAndView getPackageBooking() {
        System.out.println("viewPackages() method called");
        ModelAndView mav = new ModelAndView("packagebooking.html");
        List<Package> packages = this.packageRepository.findAll();
        mav.addObject("packages", packages);
        mav.addObject("MembershipObj", new Memberships());
        return mav;
    }

    @PostMapping("bookpackage")
    public RedirectView RequestPackage(@ModelAttribute("MembershipObj") Memberships membership,
            HttpSession session) {

        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int numOfMonths = 0;
        int freezeCount = 0;
        try {
            if(membership.getIsActivated()=="Activated")
            {
                return new RedirectView("/user/bookpackage?AlreadySubscribedInAMembership");
            }
            else if(membership.getIsActivated()=="Pending")
            {
                return new RedirectView("/user/bookpackage?RequestAlreadySentAndPending");
            }
            else{
            membership.setClientID(loggedInUser.getID());

            Optional<Package> packageOptional = Optional
                    .ofNullable(this.packageRepository.findById(membership.getPackageID()));

            if (packageOptional.isPresent()) {
                Package Package = packageOptional.get();
                numOfMonths = Package.getNumOfMonths();
                freezeCount = Package.getFreezeLimit();
            }

            LocalDate startDate = LocalDate.now();
            membership.setStartDate(startDate);

            LocalDate endDate = startDate.plusMonths(numOfMonths);
            membership.setEndDate(endDate);

            membership.setFreezeCount(freezeCount);

            membership.setFreezed("Not Freezed");

            membership.setIsActivated("Pending");

            this.membershipsRepository.save(membership);

            return new RedirectView("/user/bookpackage?RequestSent");
        }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return new RedirectView("error_page");
        }
    }


    @GetMapping("bookclass")
    public ModelAndView getClassBooking(HttpSession session) {
        ModelAndView mav = new ModelAndView("classbooking.html");

        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        List<AssignedClass> assignedClasses = this.assignedClassRepository.findAll();
        List<Classes> classes = new ArrayList<>();

        for (AssignedClass assignedClass : assignedClasses) {
            int classId = assignedClass.getClassID();
            Classes classInformation = this.classesRepository.findByID(classId);
            classes.add(classInformation);
        }
        mav.addObject("loggedInUser", loggedInUser);
        mav.addObject("assignedClasses", assignedClasses);
        mav.addObject("classes", classes);
        mav.addObject("ReservedClassObj", new ReservedClass());
        return mav;
    }

    @PostMapping("bookclass")
    public ModelAndView RequestClass(@ModelAttribute("ReservedClassObj") ReservedClass reservedClass) {

        ModelAndView modelAndView = new ModelAndView();
        try {

            AssignedClass assignedClass = this.assignedClassRepository.findByID(reservedClass.getAssignedClassID());
            double price = assignedClass.getPrice();
            if (price > 0) {
                reservedClass.setIsActivated("Pending");
            } else {
                reservedClass.setIsActivated("Activated");

            }
            this.reservedClassRepository.save(reservedClass);

            modelAndView.setViewName("redirect:/user/bookclass");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            modelAndView.setViewName("error_page");
        }
        return modelAndView;
    }

    @GetMapping("viewpackage")
    public ModelAndView viewPackage(HttpSession session) {
        ModelAndView mav = new ModelAndView("viewpackage.html");
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int id = loggedInUser.getID();
        Memberships membership = this.membershipsRepository.findByClientID(id);

        LocalDate now = LocalDate.now();
        if (membership != null && membership.getIsActivated()=="Activated" && membership.getEndDate().isAfter(now)) {
            Package packages = this.packageRepository.findById(membership.getPackageID());
            mav.addObject("membership", membership);
            mav.addObject("package", packages);
        }

        return mav;
    }


    @GetMapping("requestfreeze")
    public ModelAndView requestFreeze(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        ModelAndView mav = new ModelAndView("requestfreeze.html");
        Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());
        Package pack = null;
        if (membership != null) {
            pack = packageRepository.findById(membership.getPackageID());

            LocalDate currentDate = LocalDate.now();
            LocalDate minFreezeDate = currentDate.plusDays(3);
            LocalDate maxFreezeDate = currentDate.plusDays(membership.getFreezeCount());

            mav.addObject("minFreezeDate", minFreezeDate);
            mav.addObject("maxFreezeDate", maxFreezeDate);
        }
        mav.addObject("freezeEndDate", "");
        mav.addObject("package", pack);
        mav.addObject("membership", membership);
        mav.addObject("loggedInUser", loggedInUser);

        return mav;
    }

    @PostMapping("requestfreeze")
    public ModelAndView freezeMembership(@RequestParam("freezeEndDate") String freezeEndDate,
            HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());

        updateMembershipEndDate(membership, freezeDuration);

        createScheduledUnfreeze(membership.getID(), LocalDate.now(), LocalDate.parse(freezeEndDate));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/requestfreeze");
        return modelAndView;
    }

    @PostMapping("requestunfreeze")
    public ModelAndView unfreezeMembership(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());

        ScheduledUnfreeze scheduledUnfreeze = scheduledUnfreezeRepository.findByMembershipID(membership.getID());
        // get the difference between old freeze duration and the new freeze duration
        int newFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), LocalDate.now());
        int oldFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(),
                scheduledUnfreeze.getFreezeEndDate());
        int freezeDuration = oldFreezeDuration - newFreezeDuration;

        // update membership end date by subtracting the new freeze duration
        membership.setEndDate(membership.getEndDate().minusDays(freezeDuration));
        membership.setFreezeCount(membership.getFreezeCount() + freezeDuration);
        membership.setFreezed("Not Freezed");
        this.membershipsRepository.save(membership);

        // remove scheduled unfreeze
        this.scheduledUnfreezeRepository.delete(scheduledUnfreeze);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/requestfreeze");
        return modelAndView;
    }

    @GetMapping("profsettings")
    public ModelAndView viewProfSettings() {
        ModelAndView mav = new ModelAndView("userprofsettings.html");
        return mav;
    }

    @PostMapping("/profsettings")
    public ModelAndView updateClient(@Valid @ModelAttribute Client clientObj, BindingResult bindingResult,
            HttpSession session, @RequestParam("action") String action) {
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

    @GetMapping("viewclasses")
    public ModelAndView viewReservedClasses(HttpSession session) {
        ModelAndView mav = new ModelAndView("viewclasses.html");
    
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
    
        if (loggedInUser != null) {
            int userId = loggedInUser.getID();
    
            List<ReservedClass> reservedClasses = this.reservedClassRepository.findByClientID(userId);
    
            LocalDate now = LocalDate.now();
            if (reservedClasses.isEmpty()) {
                mav.addObject("errorMessage", "No reserved classes found for the client.");
            } else {
                List<AssignedClass> assignedClassesDetails = new ArrayList<>();
                List<Classes> reservedClassesDetails = new ArrayList<>();
                List<String> coachNames = new ArrayList<>();
                for (ReservedClass reservedClass : reservedClasses) {
                    if(reservedClass.getIsActivated()=="Activated"){
                  int classId = reservedClass.getAssignedClassID();
                    Optional<AssignedClass> assignedClassDetails = this.assignedClassRepository.findById(classId);
                    assignedClassDetails.ifPresent(assignedClassesDetails::add);
    
                    // Fetch coach name using CoachID from ReservedClass
                    Employee coachDetails = this.employeeRepository.findByID(reservedClass.getCoachID());
                    if(coachDetails != null)
                    {
                        coachNames.add(coachDetails.getName());
                    }else{
                        coachNames.add("");
                    }
                    mav.addObject("reservedClassesDetails", reservedClassesDetails);
                    mav.addObject("coaches", coachNames);
                }
                }

                
                for (AssignedClass assignedClass : assignedClassesDetails) {

                    int Id = assignedClass.getClassID();
                    Optional<Classes> ClassDetails = this.classesRepository.findById(Id);
                    ClassDetails.ifPresent(reservedClassesDetails::add);
                }
                
            }
        } else {
            mav.addObject("errorMessage", "User not logged in.");
        }
    
        return mav;
    }
    



}
