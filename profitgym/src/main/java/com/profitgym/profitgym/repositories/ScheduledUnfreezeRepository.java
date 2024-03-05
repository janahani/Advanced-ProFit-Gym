package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.ScheduledUnfreeze;

public interface ScheduledUnfreezeRepository extends JpaRepository<ScheduledUnfreeze,Integer>{

    
}
