package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.AssignedClass;


public interface AssignedClassRepository extends JpaRepository<AssignedClass,Integer>{
    AssignedClass findByID(int iD);

}