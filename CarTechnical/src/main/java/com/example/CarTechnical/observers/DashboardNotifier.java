package com.example.CarTechnical.observers;

import com.example.CarTechnical.models.ServiceRequest;

public class DashboardNotifier implements Observer {
    @Override
    public void update(ServiceRequest request) {
        System.out.println("[Dashboard] Service Request " + request.getId() + " updated to " + request.getStatus());
    }
}
