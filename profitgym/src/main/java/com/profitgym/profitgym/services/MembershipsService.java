package com.profitgym.profitgym.services;

import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.repositories.PackageRepository;
import com.profitgym.profitgym.repositories.ScheduledUnfreezeRepository;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final String baseUrl = "http://localhost:8081";

    public MembershipsService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Memberships> findAll() {
        String url = baseUrl + "/admindashboard/memberships";
        return this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Memberships>>() {
                }).getBody();
    }

    public Memberships findById(int membershipId) {
        return membershipsRepository.findById(membershipId);
    }

    public void acceptMembership(int membershipId) {
        String url = baseUrl + "/admindashboard/acceptMembership?membershipId=" + membershipId;
        this.restTemplate.postForObject(url, membershipId, Memberships.class);
    }

    public void activateMembership(int membershipId, int clientId, int packageid) {
        String url = baseUrl + "/admindashboard/activateMembership?membershipId=" + membershipId;
        this.restTemplate.postForObject(url, membershipId, Memberships.class);
    }

    public void declineMembership(int membershipId) {
        String url = baseUrl + "/admindashboard/declineMembership?membershipId=" + membershipId;
        this.restTemplate.postForObject(url, membershipId, Memberships.class);
    }

    public void deleteMembership(int membershipId) {
        String url = baseUrl + "/admindashboard/deletemembership?membershipId=" + membershipId;
        this.restTemplate.postForObject(url, membershipId, Memberships.class);
    }
    
    public void requestMembership(int clientId, int packageId) {
        String url = baseUrl + "/admindashboard/requestmembership";

        // Create a map to represent the request body data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("clientId", clientId);
        requestBody.put("packageId", packageId);

        // Set the headers to specify that the request body is in JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HTTP entity with the request body and headers
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request with the request entity
        this.restTemplate.postForObject(url, requestEntity, Memberships.class);
    }
    public void requestFreeze(int id, String freezeEndDate) {
        String url = baseUrl + "/admindashboard/requestfreeze?id=" + id + "&freezeEndDate=" + freezeEndDate;
        this.restTemplate.postForObject(url, id, Memberships.class);
    }

    public void requestUnfreeze(int id) {
        String url = baseUrl + "/admindashboard/requestunfreeze?id=" + id;
        this.restTemplate.postForObject(url, id, Memberships.class);
    }

    // ------ client side services ---------------

    public void freezeMembership(int id, String freezeEndDate) {

        String url = baseUrl + "/user/requestfreeze?id=" + id + "&freezeEndDate=" + freezeEndDate;
        this.restTemplate.postForObject(url, id, Memberships.class);

    }

    public void unfreezeMembership(int id) {
        String url = baseUrl + "/user/requestunfreeze?id=" + id;
        this.restTemplate.postForObject(url, id, Memberships.class);
    }

}
