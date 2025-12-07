package com.example.CarTechnical.strategies;

import com.example.CarTechnical.models.ServiceRequest;
import java.util.List;

public interface SchedulingStrategy {
    ServiceRequest schedule(List<ServiceRequest> pendingRequests);
}
