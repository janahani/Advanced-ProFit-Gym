package com.profitgym.packagemicroservice.packagemicroservices.repositories;

import com.profitgym.packagemicroservice.packagemicroservices.models.Package;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,Integer>{
    Package findById(int id);
}

