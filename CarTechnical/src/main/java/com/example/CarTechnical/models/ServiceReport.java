package com.example.CarTechnical.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "serviceRequest")
public class ServiceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private User mechanic;

    private LocalDateTime performedAt;

    @Column(length = 4000)
    private String summary;

    @Column(length = 2000)
    private String partsUsed;

    private Double totalCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public User getMechanic() {
        return mechanic;
    }

    public void setMechanic(User mechanic) {
        this.mechanic = mechanic;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public ServiceStatus getStatus(){
        return this.serviceRequest.getStatus();
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPartsUsed() {
        return partsUsed;
    }

    public void setPartsUsed(String partsUsed) {
        this.partsUsed = partsUsed;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public static class Builder {
        private ServiceRequest request;
        private User mechanic;
        private LocalDateTime performedAt;
        private String summary;
        private String partsUsed;
        private Double totalCost;

        public Builder serviceRequest(ServiceRequest req) { this.request = req; return this; }
        public Builder mechanic(User mechanic) { this.mechanic = mechanic; return this; }
        public Builder performedAt(LocalDateTime performedAt) { this.performedAt = performedAt; return this; }
        public Builder summary(String summary) { this.summary = summary; return this; }
        public Builder partsUsed(String partsUsed) { this.partsUsed = partsUsed; return this; }
        public Builder totalCost(Double totalCost) { this.totalCost = totalCost; return this; }

        public ServiceReport build() {
            ServiceReport sr = new ServiceReport();
            sr.serviceRequest = this.request;
            sr.mechanic = this.mechanic;
            sr.performedAt = this.performedAt;
            sr.summary = this.summary;
            sr.partsUsed = this.partsUsed;
            sr.totalCost = this.totalCost;
            return sr;
        }
    }

    public static Builder builder() { return new Builder(); }
}