package com.profitgym.profitgym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Classes;
import java.util.List;


public interface ClassesRepository extends JpaRepository<Classes,Integer>{
    Classes findByID(int iD);
}