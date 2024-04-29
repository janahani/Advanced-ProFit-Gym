package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.AssignedClass;
import java.util.List;


public interface AssignedClassRepository extends JpaRepository<AssignedClass,Integer>{
    AssignedClass findByID(int iD);

}