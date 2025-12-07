package com.example.CarTechnical.repositories;

import com.example.CarTechnical.models.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
}