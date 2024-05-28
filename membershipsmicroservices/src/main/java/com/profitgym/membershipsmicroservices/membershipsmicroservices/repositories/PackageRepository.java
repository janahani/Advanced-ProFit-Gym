package com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitgym.membershipsmicroservices.membershipsmicroservices.models.Package;

public interface PackageRepository extends JpaRepository<Package,Integer>{
    Optional<Package> findById(int id);
}
