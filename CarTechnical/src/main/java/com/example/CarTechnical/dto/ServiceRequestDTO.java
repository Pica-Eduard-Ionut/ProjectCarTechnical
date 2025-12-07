package com.example.CarTechnical.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequestDTO {
    private Long id;
    private Long vehicleId;
    private Long ownerId;
    private Long assignedMechanicId;
    private LocalDateTime requestedFrom;
    private LocalDateTime requestedTo;
    private String serviceType;
    private String priority;
    private String status;
    private LocalDateTime createdAt;

    public String getPriority() {
        return priority;
    }

    public String getServiceType() {
        return serviceType;
    }

    public LocalDateTime getRequestedTo() {
        return requestedTo;
    }

    public LocalDateTime getRequestedFrom() {
        return requestedFrom;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getId() {
        return id;
    }
}