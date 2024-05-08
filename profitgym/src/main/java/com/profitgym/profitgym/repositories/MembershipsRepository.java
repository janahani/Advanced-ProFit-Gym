package com.profitgym.profitgym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Memberships;

public interface MembershipsRepository extends JpaRepository<Memberships,Integer>{
    
    Memberships findByClientID(int ClientID); 
    List<Memberships> findByIsActivated(String isActivated);

    Memberships findById(int ID); 


}