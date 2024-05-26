package main.java.com.profitgym.membershipsmicroservices.membershipsmicroservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profitgym.profitgym.models.Memberships;
import java.util.List;

public interface MembershipsRepository extends JpaRepository<Memberships, Integer> {
    Memberships findByClientID(int clientID);
    List<Memberships> findByIsActivated(String isActivated);
}
