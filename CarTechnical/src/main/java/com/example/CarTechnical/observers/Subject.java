package com.example.CarTechnical.observers;

import com.example.CarTechnical.models.ServiceRequest;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(ServiceRequest request);
}
