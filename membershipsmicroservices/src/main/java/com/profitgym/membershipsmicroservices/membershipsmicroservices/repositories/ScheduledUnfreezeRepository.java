package com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.ScheduledUnfreeze;

public interface ScheduledUnfreezeRepository extends JpaRepository<ScheduledUnfreeze, Integer> {
    ScheduledUnfreeze findByMembershipID(int membershipID);
}
