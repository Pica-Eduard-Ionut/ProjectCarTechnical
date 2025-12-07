package com.example.CarTechnical.models;

import java.time.LocalDateTime;

public abstract class ServiceRequestFactory {
    public abstract ServiceRequest createRequest(Vehicle vehicle, User owner, LocalDateTime from, LocalDateTime to, Priority priority);

    public static ServiceRequestFactory getFactory(ServiceType type) {
        return switch (type) {
            case MAINTENANCE -> new MaintenanceRequestFactory();
            case REPAIR -> new RepairRequestFactory();
            case OIL_CHANGE -> new OilChangeRequestFactory();
            case INSPECTION -> new InspectionRequestFactory();
        };
    }
}

class MaintenanceRequestFactory extends ServiceRequestFactory {
    @Override
    public ServiceRequest createRequest(Vehicle v, User o, LocalDateTime from, LocalDateTime to, Priority p) {
        return ServiceRequest.builder()
                .vehicle(v)
                .owner(o)
                .serviceType(ServiceType.MAINTENANCE)
                .requestedFrom(from)
                .requestedTo(to)
                .priority(p)
                .status(ServiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

class RepairRequestFactory extends ServiceRequestFactory {
    @Override
    public ServiceRequest createRequest(Vehicle v, User o, LocalDateTime from, LocalDateTime to, Priority p) {
        return ServiceRequest.builder()
                .vehicle(v)
                .owner(o)
                .serviceType(ServiceType.REPAIR)
                .requestedFrom(from)
                .requestedTo(to)
                .priority(p)
                .status(ServiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

class InspectionRequestFactory extends ServiceRequestFactory {
    @Override
    public ServiceRequest createRequest(Vehicle v, User o, LocalDateTime from, LocalDateTime to, Priority p) {
        return ServiceRequest.builder()
                .vehicle(v)
                .owner(o)
                .serviceType(ServiceType.INSPECTION)
                .requestedFrom(from)
                .requestedTo(to)
                .priority(p)
                .status(ServiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

class OilChangeRequestFactory extends ServiceRequestFactory {
    @Override
    public ServiceRequest createRequest(Vehicle v, User o, LocalDateTime from, LocalDateTime to, Priority p) {
        return ServiceRequest.builder()
                .vehicle(v)
                .owner(o)
                .serviceType(ServiceType.OIL_CHANGE)
                .requestedFrom(from)
                .requestedTo(to)
                .priority(p)
                .status(ServiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}