package main.java.com.profitgym.membershipsmicroservices.membershipsmicroservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.ScheduledUnfreeze;
import com.profitgym.membershipmicroservices.repositories.MembershipsRepository;
import com.profitgym.membershipmicroservices.repositories.ScheduledUnfreezeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("")
public class MembershipsController {

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;

    @GetMapping
    public ResponseEntity<List<Memberships>> getAllMemberships() {
        List<Memberships> memberships = membershipsRepository.findAll();
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @PostMapping("/admindashboard/accept")
    public ResponseEntity<Memberships> acceptMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId).orElse(null);
        if (membership != null) {
            membership.setIsActivated("Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/admindashboard/decline")
    public ResponseEntity<Memberships> declineMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId).orElse(null);
        if (membership != null) {
            membership.setIsActivated("Not Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/admindashboard/freeze")
    public ResponseEntity<String> freezeMembership(@RequestParam("id") int id,
                                                   @RequestParam("freezeEndDate") String freezeEndDate) {
        Memberships membership = membershipsRepository.findById(id).orElse(null);
        if (membership != null) {
            int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
            updateMembershipEndDate(membership, freezeDuration);
            createScheduledUnfreeze(membership.getID(), LocalDate.now(), LocalDate.parse(freezeEndDate));
        }
        return new ResponseEntity<>("Membership frozen", HttpStatus.OK);
    }

    @PostMapping("/admindashboard/unfreeze")
    public ResponseEntity<String> unfreezeMembership(@RequestParam("id") int id) {
        Memberships membership = membershipsRepository.findById(id).orElse(null);
        if (membership != null) {
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
        }
        return new ResponseEntity<>("Membership unfrozen", HttpStatus.OK);
    }

    private int calculateFreezeDuration(LocalDate currentDate, LocalDate freezeEndDate) {
        return (int) ChronoUnit.DAYS.between(currentDate, freezeEndDate);
    }

    private void updateMembershipEndDate(Memberships membership, int freezeDuration) {
        LocalDate membershipEndDate = membership.getEndDate().plusDays(freezeDuration);
        int newFreezeCount = membership.getFreezeCount() - freezeDuration;
        membership.setFreezeCount(newFreezeCount);
        membership.setEndDate(membershipEndDate);
        membership.setFreezed("Freezed");
        membershipsRepository.save(membership);
    }

    private void createScheduledUnfreeze(int membershipID, LocalDate currentDate, LocalDate freezeEndDate) {
        ScheduledUnfreeze scheduledUnfreeze = new ScheduledUnfreeze();
        scheduledUnfreeze.setFreezeStartDate(currentDate);
        scheduledUnfreeze.setFreezeEndDate(freezeEndDate);
        scheduledUnfreeze.setMembershipID(membershipID);
        scheduledUnfreeze.setFreezeCount(membershipsRepository.findById(membershipID).orElseThrow().getFreezeCount());
        scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }

    // User side membership functionalities begin ---------------------------------------------------

    @GetMapping("/user/memberships")
    public ResponseEntity<List<Memberships>> getUserMemberships(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        List<Memberships> memberships = membershipsRepository.findByClientId(loggedInUser.getID());
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @PostMapping("/user/requestfreeze")
    public ResponseEntity<String> requestFreeze(@RequestParam("freezeEndDate") String freezeEndDate, HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        int freezeDuration = calculateFreezeDuration(LocalDate.now(), LocalDate.parse(freezeEndDate));
        Memberships membership = membershipsRepository.findByClientIdForFreeze(loggedInUser.getID());
        updateMembershipEndDate(membership, freezeDuration);
        createScheduledUnfreezeClient(membership.getID(), LocalDate.now(), LocalDate.parse(freezeEndDate));
        return new ResponseEntity<>("Membership frozen", HttpStatus.OK);
    }

    @PostMapping("/user/requestunfreeze")
    public ResponseEntity<String> requestUnfreeze(HttpSession session) {
        Client loggedInUser = (Client) session.getAttribute("loggedInUser");
        Memberships membership = membershipsRepository.findByClientIdForUnfreeze(loggedInUser.getID());
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

    // User side membership functionalities end -----------------------------------------------------


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
        scheduledUnfreeze.setFreezeCount(membershipsRepository.findById(membershipID).orElseThrow().getFreezeCount());
        scheduledUnfreezeRepository.save(scheduledUnfreeze);
    }
}