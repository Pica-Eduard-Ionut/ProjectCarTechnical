package com.example.CarTechnical.observers;

import com.example.CarTechnical.models.ServiceRequest;

public interface Observer {
    void update(ServiceRequest request);
}