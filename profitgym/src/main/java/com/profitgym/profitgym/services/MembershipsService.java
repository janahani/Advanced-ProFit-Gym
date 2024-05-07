package com.profitgym.profitgym.services;

import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipsService {

    private final MembershipsRepository membershipsRepository;

    @Autowired
    public MembershipsService(MembershipsRepository membershipsRepository) {
        this.membershipsRepository = membershipsRepository;
    }

    public Memberships addMembership(Memberships membership) {
        // Perform any necessary validation or business logic here
        // before saving the membership
        return membershipsRepository.save(membership);
    }
}