package com.cst.vaccinationcenterservice.service;

import com.cst.vaccinationcenterservice.entity.VaccinationCenter;
import com.cst.vaccinationcenterservice.repository.VaccinationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor   
public class VaccinationService {
    private final VaccinationRepository vaccinationRepository;
    public VaccinationCenter addVaccinationCenter(VaccinationCenter vaccinationCenter) {
        return vaccinationRepository.save(vaccinationCenter);
    }
    public VaccinationCenter getVaccinationCenterById(Integer id) {
        return vaccinationRepository.findById(id).get();
    }
}
