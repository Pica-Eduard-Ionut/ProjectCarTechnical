package com.example.CarTechnical.services;

import com.example.CarTechnical.models.ServiceReport;

import java.util.List;
import java.util.Optional;

public interface ServiceReportService {
    ServiceReport create(ServiceReport report);
    ServiceReport save(ServiceReport report);
    Optional<ServiceReport> findById(Long id);
    List<ServiceReport> findByMechanicId(Long id);

    List<ServiceReport> findAll();

    List<ServiceReport> findByOwnerId(Long ownerId);

    void delete(Long id);
}
