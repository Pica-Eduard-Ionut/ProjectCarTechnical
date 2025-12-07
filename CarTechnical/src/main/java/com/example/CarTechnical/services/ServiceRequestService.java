package com.example.CarTechnical.services;

import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.User;

import java.util.List;
import java.util.Optional;

public interface ServiceRequestService {
    ServiceRequest create(ServiceRequest request);
    Optional<ServiceRequest> findById(Long id);
    List<ServiceRequest> findByStatus(ServiceStatus status);
    ServiceRequest updateStatus(Long id, ServiceStatus status);
    ServiceRequest assignMechanic(Long requestId, Long mechanicId);

    List<ServiceRequest> findByOwnerId(Long id);

    ServiceRequest getNextScheduledRequest();

    void useEarliestAvailableStrategy();

    void usePriorityBasedStrategy();

    List<ServiceRequest> findByMechanic(User mechanic);

    List<ServiceRequest> findAll();

    void save(ServiceRequest request);

    void updateMechanicRequest(Long id, ServiceStatus status, String mechanicNotes, double laborCost, double partsCost, String partsUsed);
}