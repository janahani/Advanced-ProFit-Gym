package com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.Memberships;
import java.util.List;

public interface MembershipsRepository extends JpaRepository<Memberships, Integer> {
    List<Memberships> findByIsActivated(String isActivated);
    Memberships findByClientID(int ClientID); 

    Memberships findById(int ID); 

}
