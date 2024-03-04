package com.profitgym.profitgym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Package;

public interface MembershipsRepository extends JpaRepository<Package,Integer>{
    
}