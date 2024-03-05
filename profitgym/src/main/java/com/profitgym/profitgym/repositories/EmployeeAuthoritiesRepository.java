package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.EmployeeAuthorities;

public interface EmployeeAuthoritiesRepository extends JpaRepository<EmployeeAuthorities,Integer>{

    
}
