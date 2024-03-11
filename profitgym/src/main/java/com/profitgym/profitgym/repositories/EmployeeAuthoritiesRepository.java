package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.EmployeeAuthorities;
import java.util.List;


public interface EmployeeAuthoritiesRepository extends JpaRepository<EmployeeAuthorities,Integer>{

    List<EmployeeAuthorities> findByJobTitleID(int JobTitleID);
}
