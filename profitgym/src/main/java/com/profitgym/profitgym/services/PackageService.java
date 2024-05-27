package com.profitgym.profitgym.services;
import java.util.List;
 
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties.Packages;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.profitgym.profitgym.models.Package;

@Service

public class PackageService {
    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:8083";

    public PackageService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Package> findAll() {
        String url = baseUrl + "/admindashboard/packages";
    
        return this.restTemplate.exchange(
            url, 
            HttpMethod.GET, 
            null, 
            new ParameterizedTypeReference<List<Package>>() {

            }).getBody();
    }

    public void savePackage(Package packageObj){
        String url = baseUrl + "/admindashboard/addpackage";
        this.restTemplate.postForObject(url, packageObj, Package.class);
    }

    // public void packageActivation(int packageID) {
    //     String url = baseUrl + "/admindashboard/package-activation";
    //     this.restTemplate.postForObject(url, packageID, Package.class);
    // }
    
    public void activatePackage(Package packageObj){
        String url = baseUrl + "/admindashboard/activatepackage";
        this.restTemplate.postForObject(url, packageObj, Package.class);
    }

    public void deactivatePackage(Package packageObj){
        String url = baseUrl + "/admindashboard/deactivatepackage";
        this.restTemplate.postForObject(url, packageObj, Package.class);
    }

    public Package findById(int id) {

        String url = baseUrl + "/admindashboard/package/"+id;

        Package existingPackage = restTemplate.getForObject(url,Package.class);
        return existingPackage;
    }


}
