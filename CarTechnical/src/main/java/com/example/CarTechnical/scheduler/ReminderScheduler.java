package com.example.CarTechnical.scheduler;

import com.example.CarTechnical.repositories.ServiceRequestRepository;
import com.example.CarTechnical.services.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    private final ServiceRequestRepository serviceRequestRepository;
    private final NotificationService notificationService;

    public ReminderScheduler(ServiceRequestRepository serviceRequestRepository, NotificationService notificationService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.notificationService = notificationService;
    }

    // Example: run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void runReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in48 = now.plusHours(48);

    }
}