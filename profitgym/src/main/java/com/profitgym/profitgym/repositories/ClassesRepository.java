package com.profitgym.profitgym.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Classes;

public interface ClassesRepository extends JpaRepository<Classes,Integer>{
    
}