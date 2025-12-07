package com.example.CarTechnical.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceReportDTO {
    private Long id;
    private Long serviceRequestId;
    private Long mechanicId;
    private LocalDateTime performedAt;
    private String summary;
    private String partsUsed;
    private Double totalCost;

    public LocalDateTime getPerformedAt() { return performedAt; }
    public String getSummary() { return summary; }
    public String getPartsUsed() { return partsUsed; }
    public Double getTotalCost() { return totalCost; }

    public Long getServiceRequestId() { return serviceRequestId; }
}