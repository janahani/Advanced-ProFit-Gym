package com.profitgym.profitgym.controllers;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.models.ScheduledUnfreeze;
import com.profitgym.profitgym.repositories.ClientRepository;

import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.PackageRepository;
import com.profitgym.profitgym.repositories.ScheduledUnfreezeRepository;
import jakarta.servlet.http.HttpSession;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admindashboard")
public class MembershipsController {

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PackageRepository packageRespository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;



    @PostMapping("/acceptMembership")
    public ModelAndView acceptMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Activated");
            membershipsRepository.save(membership);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
    }

    @PostMapping("/declineMembership")
    public ModelAndView declineMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Not Activated");
            membershipsRepository.save(membership);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
    }


    @GetMapping("memberships")
    public ModelAndView viewMemberships() {
        ModelAndView mav = new ModelAndView("membershipAdminDash.html");
        List<Memberships> memberships = membershipsRepository.findAll();
        List<Client> clients = new ArrayList<>();
        List<Package> packages = new ArrayList<>();
        if (memberships != null) {

            LocalDate currentDate = LocalDate.now();
            LocalDate minFreezeDate = currentDate.plusDays(3);
            List<LocalDate> maxFreezeDates = new ArrayList<>();
            for (Memberships membership : memberships) {
                if (membership.getIsActivated() == "Activated") {
                    Client client = clientRepository.findById(membership.getClientID());
                    clients.add(client);
                    Package package1 = packageRespository.findById(membership.getPackageID());
                    if (packages.contains(package1) == false) {
                        packages.add(package1);
                    }

                    maxFreezeDates.add(currentDate.plusDays(membership.getFreezeCount()));
                }
            }
            mav.addObject("minFreezeDate", minFreezeDate);
            mav.addObject("maxFreezeDates", maxFreezeDates);
        }
        mav.addObject("memberships", memberships);
        mav.addObject("clients", clients);
        mav.addObject("packages", packages);
        return mav;
    }


    @PostMapping("addmembership")
    public ModelAndView saveMembership(@ModelAttribute Memberships membershipObj) {

        membershipsRepository.save(membershipObj);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admindashboard/addmembership");
        // Redirect to the add membership page
        return modelAndView;
    }

    @PostMapping("/deletemembership")
    public ModelAndView DeleteMemebership(@RequestParam("membershipId") int membershipId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            membershipsRepository.deleteById(membershipId);
            modelAndView.setViewName("redirect:/admindashboard/memberships");

        } catch (Exception e) {

            System.out.println("Error deleting employee: " + e.getMessage());

        }

        return modelAndView;
    }

    @GetMapping("requestmembership")
    public ModelAndView requestMembership(@RequestParam("id") int clientId) {
        ModelAndView mav = new ModelAndView("bookMembershipAdminDash.html");
        List<Package> packages = this.packageRespository.findAll();
        Client client = this.clientRepository.findById(clientId);
        mav.addObject("packages", packages);
        mav.addObject("client", client);
        return mav;
    }

    @PostMapping("requestmembership")
    public ModelAndView activateMembership(@RequestParam("id") int clientId, @RequestParam("packageID") int packageId) {
        ModelAndView mav = new ModelAndView();
        Package pack = this.packageRespository.findById(packageId);
        Memberships membership = new Memberships();
        membership.setClientID(clientId);
        membership.setIsActivated("Activated");
        membership.setPackage(pack);
        this.membershipsRepository.save(membership);
        mav.setViewName("redirect:/admindashboard/clients");
        return mav;
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

    public void createScheduledUnfreeze(Memberships membership, LocalDate currentDate, LocalDate freezeEnddate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEnddate);
        scheduledUnfreeze.setMembershipID(membership.getID());
        scheduledUnfreeze.setFreezeCount(membership.getFreezeCount());
        this.scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    @PostMapping("requestfreeze")
    public ModelAndView freezeMembership(@RequestParam("id") int id,
            @RequestParam("freezeEndDate") String freezeEndDate,
            HttpSession session) {
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsRepository.findById(id);

        updateMembershipEndDate(membership, freezeDuration);

        createScheduledUnfreeze(membership, LocalDate.now(), LocalDate.parse(freezeEndDate));

        return new ModelAndView("redirect:/admindashboard/memberships");
    }

    @PostMapping("requestunfreeze")
    public ModelAndView unfreezeMembership(@RequestParam("id") int id, HttpSession session) {
        Memberships membership = membershipsRepository.findById(id);
        ScheduledUnfreeze scheduledUnfreeze = scheduledUnfreezeRepository.findByMembershipID(membership.getID());
        // get the difference between old freeze duration and the new freeze duration
        int newFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), LocalDate.now());
        int oldFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(),
                scheduledUnfreeze.getFreezeEndDate());
        int freezeDuration = oldFreezeDuration - newFreezeDuration;

        membership.setEndDate(membership.getEndDate().minusDays(freezeDuration));
        membership.setFreezeCount(membership.getFreezeCount() + freezeDuration);
        membership.setFreezed("Not Freezed");
        this.membershipsRepository.save(membership);

        this.scheduledUnfreezeRepository.delete(scheduledUnfreeze);

        return new ModelAndView("redirect:/admindashboard/memberships");

    }


    
    
}
