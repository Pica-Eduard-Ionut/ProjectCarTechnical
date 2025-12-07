package com.example.CarTechnical.observers;

import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.singletons.NotificationDispatcher;

import java.util.*;

public class NotificationSubject implements Subject {
    private static final NotificationSubject INSTANCE = new NotificationSubject();
    private final List<Observer> observers = new ArrayList<>();

    private NotificationSubject() {}

    public static NotificationSubject getInstance() {
        return INSTANCE;
    }

    @Override
    public void attach(Observer o) { observers.add(o); }

    @Override
    public void detach(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers(ServiceRequest request) {
        observers.forEach(o -> o.update(request));

        // centralized notification
        NotificationDispatcher.getInstance().dispatch("SERVICE_REQUEST", "Service request updated: " + request.getId());
    }
}
