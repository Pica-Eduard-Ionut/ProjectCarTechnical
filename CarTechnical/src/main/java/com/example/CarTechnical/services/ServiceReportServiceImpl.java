package com.example.CarTechnical.services;

import com.example.CarTechnical.models.ServiceReport;
import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.repositories.ServiceReportRepository;
import com.example.CarTechnical.repositories.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceReportServiceImpl implements ServiceReportService {
    private final ServiceReportRepository reportRepository;
    private final ServiceRequestRepository requestRepository;

    public ServiceReportServiceImpl(ServiceReportRepository reportRepository, ServiceRequestRepository requestRepository) {
        this.reportRepository = reportRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public ServiceReport create(ServiceReport report) {
        ServiceRequest req = requestRepository.findById(report.getServiceRequest().getId()).orElseThrow();
        req.setReport(report);
        req.setStatus(ServiceStatus.COMPLETED);
        return reportRepository.save(report);
    }

    @Override
    public ServiceReport save(ServiceReport report) {
        return reportRepository.save(report);
    }

    @Override
    public Optional<ServiceReport> findById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public List<ServiceReport> findByMechanicId(Long id) {
        return reportRepository.findByMechanicId(id);
    }

    @Override
    public List<ServiceReport> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<ServiceReport> findByOwnerId(Long ownerId) {
        return reportRepository.findByServiceRequestOwnerId(ownerId);
    }

    @Override
    public void delete(Long id) {
        ServiceReport report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ServiceReport with id " + id + " not found"));

        ServiceRequest req = report.getServiceRequest();
        if (req != null) {
            req.setReport(null);
            requestRepository.save(req);
        }

        reportRepository.delete(report);
    }
}
