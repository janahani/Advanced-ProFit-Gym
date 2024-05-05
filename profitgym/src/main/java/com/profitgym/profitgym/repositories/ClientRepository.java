package com.profitgym.profitgym.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profitgym.profitgym.models.Client;

public interface ClientRepository extends JpaRepository<Client,Integer>{
    Client findByEmail(String email);
    Client findById(int id);
    List<Client> findTop5ByOrderByCreatedAtDesc();
}
