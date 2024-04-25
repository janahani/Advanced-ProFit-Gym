package com.profitgym.profitgym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String Email);
    List<Employee> findByJobTitle(int JobTitle);


}
