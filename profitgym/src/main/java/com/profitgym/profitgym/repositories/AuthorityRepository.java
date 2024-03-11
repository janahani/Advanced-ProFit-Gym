package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Authority;
import java.util.List;


public interface AuthorityRepository extends JpaRepository<Authority,Integer>{
    Authority findByID(int iD);
    
}
