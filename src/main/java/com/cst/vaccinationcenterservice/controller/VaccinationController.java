package com.cst.vaccinationcenterservice.controller;

import com.cst.vaccinationcenterservice.dto.RequiredResponse;
import com.cst.vaccinationcenterservice.entity.VaccinationCenter;
import com.cst.vaccinationcenterservice.service.VaccinationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/vaccinationApi")
@AllArgsConstructor
@Slf4j
public class VaccinationController {

    private final VaccinationService vaccinationService;

    public static final String CENTER_SERVICE = "centerService";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/add")
    public ResponseEntity<VaccinationCenter> addVaccinationCenter(@RequestBody VaccinationCenter vaccinationCenter) {
        VaccinationCenter newCenter = vaccinationService.addVaccinationCenter(vaccinationCenter);
        return new ResponseEntity<>(newCenter, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getById/{id}")
    @CircuitBreaker(name = CENTER_SERVICE, fallbackMethod = "handleCitizenDownTime")
    public ResponseEntity<RequiredResponse> getAllDataById(@PathVariable Integer id) {
        RequiredResponse response = new RequiredResponse();
        //Get Vaccination Center
        VaccinationCenter vaccinationCenter = vaccinationService.getVaccinationCenterById(id);
        response.setCenter(vaccinationCenter);
        System.out.println(vaccinationCenter  + "vaccinationCenter service triggered ****************************************************");
        //Get Citizens list registered with center
        List listOfCitizens = restTemplate.getForObject("http://CITIZEN-SERVICE/citizenApi/getById/"+id, List.class);
        response.setCitizens(listOfCitizens);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<RequiredResponse> handleCitizenDownTime(@PathVariable Integer id, Exception e) {
        RequiredResponse response = new RequiredResponse();
        //Get Vaccination Center
        System.out.println("Circuit breaker triggered **************************************** ");
        VaccinationCenter vaccinationCenter = vaccinationService.getVaccinationCenterById(id);
        response.setCenter(vaccinationCenter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
