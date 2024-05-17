package com.profitgym.profitgym.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.AssignedClass;


public interface AssignedClassRepository extends JpaRepository<AssignedClass,Integer>{
    AssignedClass findByID(int iD);
    List<AssignedClass> findByCoachID(int coachID);

}