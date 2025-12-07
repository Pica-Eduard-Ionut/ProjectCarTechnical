package com.example.CarTechnical.services;

import com.example.CarTechnical.models.ServiceReport;
import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.User;
import com.example.CarTechnical.observers.DashboardNotifier;
import com.example.CarTechnical.observers.EmailNotifier;
import com.example.CarTechnical.observers.NotificationSubject;
import com.example.CarTechnical.repositories.ServiceRequestRepository;
import com.example.CarTechnical.repositories.UserRepository;
import com.example.CarTechnical.strategies.PriorityBasedStrategy;
import com.example.CarTechnical.strategies.SchedulingStrategy;
import com.example.CarTechnical.strategies.EarliestAvailableStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final NotificationSubject notificationSubject = NotificationSubject.getInstance();

    private SchedulingStrategy schedulingStrategy = new PriorityBasedStrategy();

    public ServiceRequestServiceImpl(ServiceRequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;

        notificationSubject.attach(new DashboardNotifier());
        notificationSubject.attach(new EmailNotifier());
    }

    @Override
    public ServiceRequest create(ServiceRequest request) {
        request.setStatus(ServiceStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());
        return requestRepository.save(request);
    }

    @Override
    public Optional<ServiceRequest> findById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    public List<ServiceRequest> findByStatus(ServiceStatus status) {
        if (status == null) {
            return requestRepository.findAll();
        }
        return requestRepository.findByStatus(status);
    }

    @Override
    public ServiceRequest updateStatus(Long id, ServiceStatus status) {
        ServiceRequest req = requestRepository.findById(id).orElseThrow();
        req.setStatus(status);
        ServiceRequest updated = requestRepository.save(req);
        notificationSubject.notifyObservers(updated);
        return updated;
    }

    @Override
    public ServiceRequest assignMechanic(Long requestId, Long mechanicId) {
        ServiceRequest req = requestRepository.findById(requestId).orElseThrow();
        User mech = userRepository.findById(mechanicId).orElseThrow();
        req.setAssignedMechanic(mech);
        req.setStatus(ServiceStatus.CONFIRMED);
        return requestRepository.save(req);
    }

    @Override
    public List<ServiceRequest> findByOwnerId(Long ownerId) {
        return requestRepository.findByOwner_Id(ownerId);
    }

    @Override
    public List<ServiceRequest> findByMechanic(User mechanic) {
        return requestRepository.findByAssignedMechanic(mechanic);
    }

    @Override
    public List<ServiceRequest> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public void save(ServiceRequest request) {
        if (request.getId() == null) {
            request.setStatus(ServiceStatus.PENDING);
            request.setCreatedAt(LocalDateTime.now());
        }
        requestRepository.save(request);
    }

    public ServiceRequest getNextScheduledRequest() {
        List<ServiceRequest> pending = requestRepository.findByStatus(ServiceStatus.PENDING);
        return schedulingStrategy.schedule(pending);
    }

    public void setSchedulingStrategy(SchedulingStrategy strategy) {
        this.schedulingStrategy = strategy;
    }

    public void useEarliestAvailableStrategy() {
        this.schedulingStrategy = new EarliestAvailableStrategy();
    }

    @Override
    public void usePriorityBasedStrategy() {
        this.schedulingStrategy = new PriorityBasedStrategy();
    }

    public void updateMechanicRequest(Long requestId,
                                      ServiceStatus status,
                                      String mechanicNotes,
                                      double laborCost,
                                      double partsCost,
                                      String partsUsed) {
        // Fetch the service request
        ServiceRequest req = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(status);

        ServiceReport report = req.getReport();
        if (report == null) {
            report = new ServiceReport();
            report.setServiceRequest(req);
            report.setMechanic(req.getAssignedMechanic());
        }

        report.setPerformedAt(LocalDateTime.now());
        report.setSummary(mechanicNotes);
        report.setPartsUsed(partsUsed != null ? partsUsed : "");
        report.setTotalCost(laborCost + partsCost);

        req.setReport(report);

        requestRepository.save(req);
    }
}
