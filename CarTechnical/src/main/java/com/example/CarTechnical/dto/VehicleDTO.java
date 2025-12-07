package com.example.CarTechnical.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private String make;
    private String model;
    private Integer year;
    private String vin;
    private Long mileage;
    private LocalDate lastServiceDate;
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getVin() {
        return vin;
    }

    public Long getMileage() {
        return mileage;
    }

    public LocalDate getLastServiceDate() {
        return lastServiceDate;
    }
}