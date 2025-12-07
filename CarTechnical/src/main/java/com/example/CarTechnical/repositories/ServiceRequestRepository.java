package com.example.CarTechnical.repositories;


import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByStatus(ServiceStatus status);
    List<ServiceRequest> findByAssignedMechanic(User mechanic);
    List<ServiceRequest> findByOwner_Id(Long ownerId);
}