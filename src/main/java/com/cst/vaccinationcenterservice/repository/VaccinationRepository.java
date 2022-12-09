package com.cst.vaccinationcenterservice.repository;

import com.cst.vaccinationcenterservice.entity.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepository extends JpaRepository<VaccinationCenter, Integer> {
}
