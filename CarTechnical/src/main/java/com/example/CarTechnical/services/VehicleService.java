package com.example.CarTechnical.services;

import com.example.CarTechnical.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle create(Vehicle vehicle);
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findByOwnerId(Long ownerId);
    Vehicle update(Long id, Vehicle vehicle);
    void delete(Long id);
}