package com.profitgym.profitgym.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.profitgym.models.Client;

public interface ClientRepository extends JpaRepository<Client,Integer>{
    
}