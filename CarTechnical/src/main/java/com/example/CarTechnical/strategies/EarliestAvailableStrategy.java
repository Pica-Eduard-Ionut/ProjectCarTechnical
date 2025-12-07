package com.example.CarTechnical.strategies;

import com.example.CarTechnical.models.ServiceRequest;
import java.util.Comparator;
import java.util.List;

public class EarliestAvailableStrategy implements SchedulingStrategy {
    @Override
    public ServiceRequest schedule(List<ServiceRequest> pending) {
        return pending.stream()
                .min(Comparator.comparing(ServiceRequest::getRequestedFrom))
                .orElse(null);
    }
}
