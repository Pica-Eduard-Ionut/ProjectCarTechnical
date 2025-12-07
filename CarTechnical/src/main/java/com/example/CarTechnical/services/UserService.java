package com.example.CarTechnical.services;

import com.example.CarTechnical.models.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAllMechanics();
    List<User> findAll();
}