package com.profitgym.profitgym.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.ReservedClass;

public interface ReservedClassRepository extends JpaRepository<ReservedClass,Integer>{
     ReservedClass findByID(int iD);
     List<ReservedClass> findByIsActivated(String isActivated);
     List<ReservedClass> findByClientID(int clientID);

}
