package com.example.CarTechnical.observers;

import com.example.CarTechnical.models.ServiceRequest;

public class EmailNotifier implements Observer {
    @Override
    public void update(ServiceRequest request) {
        System.out.println("[Email] Sending update email for request " + request.getId());
    }
}
