package com.example.CarTechnical.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"vehicles", "requests", "notifications"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRequest> requests = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEvent> notifications = new ArrayList<>();

    public User addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        vehicle.setOwner(this);
        return this;
    }

    public User addRequest(ServiceRequest request) {
        requests.add(request);
        request.setOwner(this);
        return this;
    }

    public User addNotification(NotificationEvent notification) {
        notifications.add(notification);
        notification.setUser(this);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.setPasswordHash(encoder.encode(password));
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<ServiceRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<ServiceRequest> requests) {
        this.requests = requests;
    }

    public List<NotificationEvent> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationEvent> notifications) {
        this.notifications = notifications;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private Role role;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.name = this.name;
            user.email = this.email;
            user.role = this.role;
            if (this.password != null) {
                user.setPassword(this.password);
            }
            return user;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}