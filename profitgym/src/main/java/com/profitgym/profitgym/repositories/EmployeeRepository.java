package com.profitgym.profitgym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String Email);

}
