package com.profitgym.profitgym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.models.ScheduledUnfreeze;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.PackageRepository;
import com.profitgym.profitgym.repositories.ScheduledUnfreezeRepository;
import com.profitgym.profitgym.services.MembershipsService;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("")

public class MembershipsController {

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;

    @Autowired
    private MembershipsService membershipsService;


    @PostMapping("/admindashboard/acceptMembership")
    public ModelAndView acceptMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = this.membershipsService.findMembershipById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Activated");
            membershipsService.saveMembership(membership);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
    }

    @PostMapping("/admindashboard/declineMembership")
    public ModelAndView declineMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = this.membershipsService.findMembershipById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Not Activated");
            membershipsService.saveMembership(membership);
        }
        return new ModelAndView("redirect:/admindashboard/clientrequests");
    }

    @GetMapping("/admindashboard/memberships")
    public ModelAndView viewMemberships() {
        ModelAndView mav = new ModelAndView("membershipAdminDash.html");
        List<Memberships> memberships = this.membershipsService.findAll();
        List<Client> clients = new ArrayList<>();
        List<Package> packages = new ArrayList<>();
        if (memberships != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate minFreezeDate = currentDate.plusDays(3);
            List<LocalDate> maxFreezeDates = new ArrayList<>();
            for (Memberships membership : memberships) {
                if ("Activated".equals(membership.getIsActivated())) {
                    Client client = clientRepository.findById(membership.getClientID());
                    clients.add(client);
                    Package package1 = packageRepository.findById(membership.getPackageID());
                    if (!packages.contains(package1)) {
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

    @PostMapping("/admindashboard/deletemembership")
    public ModelAndView deleteMembership(@RequestParam("membershipId") int membershipId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            membershipsService.deleteMembership(membershipId);
            modelAndView.setViewName("redirect:/admindashboard/memberships");
        } catch (Exception e) {
            System.out.println("Error deleting membership: " + e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/admindashboard/requestmembership")
    public ModelAndView requestMembership(@RequestParam("id") int clientId) {
        ModelAndView mav = new ModelAndView("bookMembershipAdminDash.html");
        List<Package> packages = packageRepository.findAll();
        Client client = clientRepository.findById(clientId);
        mav.addObject("packages", packages);
        mav.addObject("client", client);
        return mav;
    }


    @PostMapping("/admindashboard/requestmembership")
    public ModelAndView activateMembership(@RequestParam("id") int clientId, @RequestParam("packageID") int packageId) {
        ModelAndView mav = new ModelAndView();
        Package pack = packageRepository.findById(packageId);
        Memberships membership = new Memberships();
        membership.setClientID(clientId);
        membership.setIsActivated("Activated");
        membership.setPackage(pack);
        membershipsService.saveMembership(membership);
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
        this.membershipsService.saveMembership(membership);
    }

    public void createScheduledUnfreezeAdmin(Memberships membership, LocalDate currentDate, LocalDate freezeEnddate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEnddate);
        scheduledUnfreeze.setMembershipID(membership.getID());
        scheduledUnfreeze.setFreezeCount(membership.getFreezeCount());
        this.scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    public void createScheduledUnfreezeClient(int membershipID, LocalDate currentDate, LocalDate freezeEnddate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEnddate);
        scheduledUnfreeze.setMembershipID(membershipID);
        this.scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    @PostMapping("/admindashboard/requestfreeze")
    public ModelAndView freezeMembership(@RequestParam("id") int id,
                                          @RequestParam("freezeEndDate") String freezeEndDate,
                                          HttpSession session) {
        membershipsService.freezeMembership(id, freezeEndDate, session);
        return new ModelAndView("redirect:/admindashboard/memberships");
    }

    @PostMapping("/admindashboard/requestunfreeze")
    public ModelAndView unfreezeMembership(@RequestParam("id") int id,
                                          HttpSession session) {
        membershipsService.unfreezeMembership(id, session);
        return new ModelAndView("redirect:/admindashboard/memberships");
    }

    // @PostMapping("/admindashboard/requestunfreeze")
    // public ModelAndView unfreezeMembership(@RequestParam("id") int id, HttpSession session) {
    //     Memberships membership = membershipsService.findMembershipById(id);
    //     ScheduledUnfreeze scheduledUnfreeze = scheduledUnfreezeRepository.findByMembershipID(membership.getID());
    //     // get the difference between old freeze duration and the new freeze duration
    //     int newFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), LocalDate.now());
    //     int oldFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(),
    //             scheduledUnfreeze.getFreezeEndDate());
    //     int freezeDuration = oldFreezeDuration - newFreezeDuration;

    //     membership.setEndDate(membership.getEndDate().minusDays(freezeDuration));
    //     membership.setFreezeCount(membership.getFreezeCount() + freezeDuration);
    //     membership.setFreezed("Not Freezed");
    //     this.membershipsService.saveMembership(membership);

    //     this.scheduledUnfreezeRepository.delete(scheduledUnfreeze);

    //     return new ModelAndView("redirect:/admindashboard/memberships");

    // }



    // client side membership functionalities begin ---------------------------------------------------


    
    @PostMapping("/user/requestfreeze")
    public ModelAndView freezeMembership(@RequestParam("freezeEndDate") String freezeEndDate,
            HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsService.findByClientID(loggedInUser.getID());

        updateMembershipEndDate(membership, freezeDuration);

        createScheduledUnfreezeClient(membership.getID(), LocalDate.now(), LocalDate.parse(freezeEndDate));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/requestfreeze");
        return modelAndView;
    }

    @PostMapping("/user/requestunfreeze")
    public ModelAndView unfreezeMembership(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        Memberships membership = membershipsService.findByClientID(loggedInUser.getID());

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
        this.membershipsService.saveMembership(membership);

        // remove scheduled unfreeze
        this.scheduledUnfreezeRepository.delete(scheduledUnfreeze);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/requestfreeze");
        return modelAndView;
    }



    
    
}
