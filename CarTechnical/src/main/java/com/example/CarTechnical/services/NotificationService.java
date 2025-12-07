package com.example.CarTechnical.services;

public interface NotificationService {
    void sendReminder(Long userId, String payload);
}