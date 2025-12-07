package com.example.CarTechnical.config;

import com.example.CarTechnical.models.*;
import com.example.CarTechnical.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceReportRepository serviceReportRepository;

    public DataLoader(UserRepository userRepository,
                      VehicleRepository vehicleRepository,
                      ServiceRequestRepository serviceRequestRepository,
                      ServiceReportRepository serviceReportRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceReportRepository = serviceReportRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("📦 Sample data already loaded.");
            return;
        }

        System.out.println("🚀 Loading sample data...");

        // --- USERS ---
        User admin = User.builder()
                .name("Admin User")
                .email("admin@cartech.com")
                .role(Role.ADMIN)
                .build();
        admin.setPassword("admin123");

        User mechanic = User.builder()
                .name("John Mechanic")
                .email("mechanic@cartech.com")
                .role(Role.MECHANIC)
                .build();
        mechanic.setPassword("mechanic123");

        User owner = User.builder()
                .name("Alice Owner")
                .email("owner@cartech.com")
                .role(Role.OWNER)
                .build();
        owner.setPassword("owner123");

        userRepository.save(admin);
        userRepository.save(mechanic);
        userRepository.save(owner);

        // --- VEHICLES ---
        Vehicle v1 = Vehicle.builder()
                .make("Toyota")
                .model("Corolla")
                .year(2018)
                .vin("JTDBL40E799123456")
                .mileage(75000L)
                .lastServiceDate(LocalDate.now().minusMonths(4))
                .owner(owner)
                .build();

        Vehicle v2 = Vehicle.builder()
                .make("Ford")
                .model("Focus")
                .year(2020)
                .vin("1FAFP34P95W123789")
                .mileage(42000L)
                .lastServiceDate(LocalDate.now().minusMonths(2))
                .owner(owner)
                .build();

        vehicleRepository.save(v1);
        vehicleRepository.save(v2);

        // --- SERVICE REQUESTS ---
        ServiceRequest req1 = ServiceRequest.builder()
                .vehicle(v1)
                .owner(owner)
                .mechanic(mechanic)
                .requestedFrom(LocalDateTime.now().plusDays(2))
                .requestedTo(LocalDateTime.now().plusDays(3))
                .serviceType(ServiceType.OIL_CHANGE)
                .priority(Priority.NORMAL)
                .status(ServiceStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();

        ServiceRequest req2 = ServiceRequest.builder()
                .vehicle(v2)
                .owner(owner)
                .mechanic(mechanic)
                .requestedFrom(LocalDateTime.now().plusDays(5))
                .requestedTo(LocalDateTime.now().plusDays(6))
                .serviceType(ServiceType.REPAIR)
                .priority(Priority.HIGH)
                .status(ServiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        serviceRequestRepository.save(req1);
        serviceRequestRepository.save(req2);

        // --- SERVICE REPORT ---
        ServiceReport report = ServiceReport.builder()
                .serviceRequest(req1)
                .mechanic(mechanic)
                .performedAt(LocalDateTime.now().plusDays(3))
                .summary("Changed oil and filter. Checked tire pressure.")
                .partsUsed("Oil filter, Engine oil")
                .totalCost(150.0)
                .build();

        req1.attachReport(report);

        serviceReportRepository.save(report);
        serviceRequestRepository.save(req1);

        System.out.println("✅ Sample data successfully loaded!");
    }
}

