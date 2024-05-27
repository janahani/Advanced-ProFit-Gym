package com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.Package;

public interface PackageRepository extends JpaRepository<Package,Integer>{
    Package findById(int id);
}
