package com.profitgym.membershipmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profitgym.profitgym.models.ScheduledUnfreeze;

public interface ScheduledUnfreezeRepository extends JpaRepository<ScheduledUnfreeze, Integer> {
    ScheduledUnfreeze findByMembershipID(int membershipID);
}
