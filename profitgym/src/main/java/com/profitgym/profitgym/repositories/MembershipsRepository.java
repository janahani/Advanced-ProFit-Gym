package com.profitgym.profitgym.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Memberships;

public interface MembershipsRepository extends JpaRepository<Memberships,Integer>{
    Memberships findByClientID(int ClientID); 
    
}