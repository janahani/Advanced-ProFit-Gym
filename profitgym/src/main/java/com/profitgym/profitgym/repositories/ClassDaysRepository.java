package com.profitgym.profitgym.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.ClassDays;

public interface ClassDaysRepository extends JpaRepository<ClassDays,Integer>{

    List<ClassDays> findByClassID(int ClassID);

}
