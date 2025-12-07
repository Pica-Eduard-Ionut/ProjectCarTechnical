package com.example.CarTechnical.repositories;

import com.example.CarTechnical.models.ServiceReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceReportRepository extends JpaRepository<ServiceReport, Long> {
    List<ServiceReport> findByMechanicId(Long mechanicId);

    List<ServiceReport> findByServiceRequestOwnerId(Long ownerId);
}