package com.profitgym.membershipsmicroservices.membershipsmicroservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.Client;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.Memberships;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.ScheduledUnfreeze;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories.MembershipsRepository;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories.ScheduledUnfreezeRepository;

import jakarta.servlet.http.HttpSession;

import java.io.Console;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("")
public class MembershipsController {

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;

    @GetMapping("/admindashboard/memberships")
    public ResponseEntity<List<Memberships>> getAllMemberships() {
        System.out.println("Request received to fetch all memberships");
        List<Memberships> memberships = membershipsRepository.findAll();
        System.out.println(memberships.size());
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @DeleteMapping("/admindashboard/deletemembership")
    public ResponseEntity<String> deleteMembership(@RequestParam("membershipId") int membershipId) {
        try {
            System.out.println("Request received to delete membership with ID: {}" + membershipId);
            membershipsRepository.deleteById(membershipId);
            System.out.println("Membership with ID {} deleted successfully" + membershipId);
            return ResponseEntity.ok("Membership deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting membership with ID {}: {}" + membershipId + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting membership: " + e.getMessage());
        }
    }

    @PostMapping("/admindashboard/acceptMembership")
    public ResponseEntity<Memberships> acceptMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/admindashboard/declineMembership")
    public ResponseEntity<Memberships> declineMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId);
        if (membership != null) {
            membership.setIsActivated("Not Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @GetMapping("/admindashboard/memberships/{id}")
    public ResponseEntity<Memberships> getMembershipById(@PathVariable int id) {
        Memberships membership = membershipsRepository.findById(id);
        if (membership != null) {
            return new ResponseEntity<>(membership, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admindashboard/requestfreeze")
    public ResponseEntity<String> freezeMembership(@RequestParam("id") int id,
            @RequestParam("freezeEndDate") String freezeEndDate,
            HttpSession session) {
        System.out.println("Request received to freeze membership with ID: " + id);
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsRepository.findById(id);
        System.out.println("Freezing membership with ID: " + id + " for " + freezeDuration + " days.");

        updateMembershipEndDate(membership, freezeDuration);
        System.out.println("Membership end date updated after freezing.");

        createScheduledUnfreezeAdmin(membership, LocalDate.now(), LocalDate.parse(freezeEndDate));
        System.out.println("Scheduled unfreeze created for membership with ID: " + id);

        return new ResponseEntity<>("Membership frozen", HttpStatus.OK);
    }

    @PostMapping("/admindashboard/requestunfreeze")
public ResponseEntity<String> unfreezeMembership(@RequestParam("id") int id) {
    Memberships membership = membershipsRepository.findById(id);
    if (membership != null) {
        ScheduledUnfreeze scheduledUnfreeze = scheduledUnfreezeRepository.findByMembershipID(membership.getID());
        int newFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), LocalDate.now());
        int oldFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), scheduledUnfreeze.getFreezeEndDate());
        int freezeDuration = oldFreezeDuration - newFreezeDuration;

        membership.setEndDate(membership.getEndDate().minusDays(freezeDuration));
        membership.setFreezeCount(membership.getFreezeCount() + freezeDuration);
        membership.setFreezed("Not Freezed");
        membershipsRepository.save(membership);
        scheduledUnfreezeRepository.delete(scheduledUnfreeze);
    }
    return new ResponseEntity<>("Membership unfrozen", HttpStatus.OK);
}

    
    
    public int calculateFreezeDuration(LocalDate currentDate, LocalDate freezeEndDate) {
        int freezeDuration = (int) ChronoUnit.DAYS.between(currentDate, freezeEndDate);
        return freezeDuration;
    }

    // User side membership functionalities begin
    // ---------------------------------------------------

    @GetMapping("/user/memberships")
    public ResponseEntity<List<Memberships>> getUserMemberships(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        Memberships memberships = membershipsRepository.findByClientID(loggedInUser.getID());
        List<Memberships> membershipsList = new ArrayList<>();
        if (memberships != null) {
            membershipsList.add(memberships);
        }
        return new ResponseEntity<>(membershipsList, HttpStatus.OK);
    }

    @PostMapping("/user/requestfreeze")
    public ResponseEntity<String> requestFreeze(@RequestParam("freezeEndDate") String freezeEndDate,
            HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());
        updateMembershipEndDate(membership, freezeDuration);
        createScheduledUnfreezeClient(membership.getID(), LocalDate.now(), LocalDate.parse(freezeEndDate));
        return new ResponseEntity<>("Membership frozen", HttpStatus.OK);
    }

    @PostMapping("/user/requestunfreeze")
    public ResponseEntity<String> requestUnfreeze(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        Memberships membership = membershipsRepository.findByClientID(loggedInUser.getID());
        ScheduledUnfreeze scheduledUnfreeze = scheduledUnfreezeRepository.findByMembershipID(membership.getID());
        int newFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(), LocalDate.now());
        int oldFreezeDuration = calculateFreezeDuration(scheduledUnfreeze.getFreezeStartDate(),
                scheduledUnfreeze.getFreezeEndDate());
        int freezeDuration = oldFreezeDuration - newFreezeDuration;
        membership.setEndDate(membership.getEndDate().minusDays(freezeDuration));
        membership.setFreezeCount(membership.getFreezeCount() + freezeDuration);
        membership.setFreezed("Not Freezed");
        membershipsRepository.save(membership);
        scheduledUnfreezeRepository.delete(scheduledUnfreeze);
        return new ResponseEntity<>("Membership unfrozen", HttpStatus.OK);
    }

    // User side membership functionalities end
    // -----------------------------------------------------

    private void updateMembershipEndDate(Memberships membership, int freezeDuration) {
        LocalDate membershipEndDate = membership.getEndDate().plusDays(freezeDuration);
        int newFreezeCount = membership.getFreezeCount() - freezeDuration;
        membership.setFreezeCount(newFreezeCount);
        membership.setEndDate(membershipEndDate);
        membership.setFreezed("Freezed");
        membershipsRepository.save(membership);
    }

    private void createScheduledUnfreezeClient(int membershipID, LocalDate currentDate, LocalDate freezeEndDate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEndDate);
        scheduledUnfreeze.setMembershipID(membershipID);
        scheduledUnfreeze.setFreezeCount(membershipsRepository.findById(membershipID).getFreezeCount());
        scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    public void createScheduledUnfreezeAdmin(Memberships membership, LocalDate currentDate, LocalDate freezeEnddate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEnddate);
        scheduledUnfreeze.setMembershipID(membership.getID());
        scheduledUnfreeze.setFreezeCount(membership.getFreezeCount());
        this.scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }
}