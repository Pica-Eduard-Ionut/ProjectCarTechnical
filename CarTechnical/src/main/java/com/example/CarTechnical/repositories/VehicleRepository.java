package com.example.CarTechnical.repositories;

import com.example.CarTechnical.models.Vehicle; // import your entity
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwnerId(Long ownerId);
}