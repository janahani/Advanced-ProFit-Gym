package com.profitgym.profitgym.services;

import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.PackageRepository;
import com.profitgym.profitgym.repositories.ScheduledUnfreezeRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MembershipsService {

    @Autowired
    private MembershipsRepository membershipsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ScheduledUnfreezeRepository scheduledUnfreezeRepository;

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8082";

    public List<Memberships> findAll() {
        String url = baseUrl + "/admindashboard/memberships";
        return this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Memberships>>() {
                }).getBody();
    }

    public MembershipsService() {
        this.restTemplate = new RestTemplate();
    }

    public void saveMembership(Memberships membership) {
        String url = baseUrl + "/admindashboard/memberships";
        String url2 = baseUrl + "/admindashboard/acceptMembership";
        String url4 = baseUrl + "/admindashboard/declineMembership";
        String url5 = baseUrl + "/admindashboard/requestmembership";
        String url6 = baseUrl + "/admindashboard/requestunfreeze";
        String url7 = baseUrl + "/user/requestunfreeze";
        String url8 = baseUrl + "/admindashboard/requestfreeze";

        this.restTemplate.postForObject(url, membership, Memberships.class);
        this.restTemplate.postForObject(url2, membership, Memberships.class);
        this.restTemplate.postForObject(url4, membership, Memberships.class);
        this.restTemplate.postForObject(url5, membership, Memberships.class);
        this.restTemplate.postForObject(url6, membership, Memberships.class);
        this.restTemplate.postForObject(url7, membership, Memberships.class);
        this.restTemplate.postForObject(url8, membership, Memberships.class);

    }

    @PostMapping("/admindashboard/requestfreeze")
    public ModelAndView freezeMembership(@RequestParam("id") int id,
                                          @RequestParam("freezeEndDate") String freezeEndDate,
                                          HttpSession session) {
        String url = baseUrl + "/admindashboard/requestfreeze?id=" + id + "&freezeEndDate=" + freezeEndDate;
        restTemplate.postForEntity(url, null, String.class);
        return new ModelAndView("redirect:/admindashboard/memberships");
    }

    @PostMapping("/admindashboard/requestunfreeze")
    public ModelAndView unfreezeMembership(@RequestParam("id") int id,
                                          HttpSession session) {
        String url = baseUrl + "/admindashboard/requestunfreeze?id=" + id;
        restTemplate.postForEntity(url, null, String.class);
        return new ModelAndView("redirect:/admindashboard/memberships");
    }
    
    
    
    public Memberships findMembershipById(int membershipId) {
        String url = baseUrl + "/admindashboard/memberships/" + membershipId;
        return restTemplate.getForObject(url, Memberships.class);
    }

    public void deleteMembership(int membershipId) {
        String url = baseUrl + "/admindashboard/deletemembership?membershipId=" + membershipId;
        restTemplate.delete(url);
    }

    public Memberships findByClientID(int clientId) {
        String freezeUrl = baseUrl + "/user/requestfreeze";
        Memberships memberships = restTemplate.getForObject(freezeUrl, Memberships.class, clientId);

        if (memberships != null) {
            return memberships;
        } else {
            String unfreezeUrl = baseUrl + "/user/requestunfreeze";
            return restTemplate.getForObject(unfreezeUrl, Memberships.class, clientId);
        }

    }
}
