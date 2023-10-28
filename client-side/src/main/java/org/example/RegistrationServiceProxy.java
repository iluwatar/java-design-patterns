package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class RegistrationServiceProxy {
    @Value("${user_registration_url}")
    String userRegistrationUrl;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HttpRequest request;
    public void registerUser(String emailAddress,String password){
        restTemplate.postForEntity(userRegistrationUrl,request,HttpResponse.class);
    }
}
