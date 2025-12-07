package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.Role;
import com.example.CarTechnical.models.User;
import com.example.CarTechnical.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role,
                           @RequestParam(required = false) String name) {

        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/login?error=user_exists";
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name != null ? name : "User");  // now uses real username
        user.setPassword(password); // hashing happens elsewhere
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "redirect:/login?logout";
    }
}
