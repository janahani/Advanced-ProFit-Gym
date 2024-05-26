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
@RequestMapping("/memberships")
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

    @PostMapping("/accept")
    public ResponseEntity<Memberships> acceptMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId).orElse(null);
        if (membership != null) {
            membership.setIsActivated("Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/decline")
    public ResponseEntity<Memberships> declineMembership(@RequestParam("membershipId") int membershipId) {
        Memberships membership = membershipsRepository.findById(membershipId).orElse(null);
        if (membership != null) {
            membership.setIsActivated("Not Activated");
            membershipsRepository.save(membership);
        }
        return new ResponseEntity<>(membership, HttpStatus.OK);
    }

    @PostMapping("/freeze")
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

    @PostMapping("/unfreeze")
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
}
