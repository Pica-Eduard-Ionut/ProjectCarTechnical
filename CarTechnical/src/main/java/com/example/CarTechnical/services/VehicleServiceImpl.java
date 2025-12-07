package com.example.CarTechnical.services;

import com.example.CarTechnical.models.Vehicle;
import com.example.CarTechnical.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        // Validate VIN uniqueness or other business rules here
        return (Vehicle) vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> findByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Override
    public Vehicle update(Long id, Vehicle vehicle) {
        Vehicle existing = (Vehicle) vehicleRepository.findById(id).orElseThrow();
        existing.setMake(vehicle.getMake());
        existing.setModel(vehicle.getModel());
        existing.setYear(vehicle.getYear());
        existing.setVin(vehicle.getVin());
        existing.setMileage(vehicle.getMileage());
        existing.setLastServiceDate(vehicle.getLastServiceDate());
        return (Vehicle) vehicleRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }
}
