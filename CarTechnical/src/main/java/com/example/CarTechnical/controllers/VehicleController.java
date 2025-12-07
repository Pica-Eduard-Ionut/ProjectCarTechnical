package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.User;
import com.example.CarTechnical.models.Vehicle;
import com.example.CarTechnical.services.UserService;
import com.example.CarTechnical.services.VehicleService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    public VehicleController(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @GetMapping
    public String listVehicles(Model model, Authentication auth) {
        User owner = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("vehicles", vehicleService.findByOwnerId(owner.getId()));
        return "vehicles/list";
    }

    @GetMapping("/view/{id}")
    public String viewVehicle(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id).orElseThrow());
        return "vehicles/view";
    }

    @GetMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id).orElseThrow());
        return "vehicles/edit";
    }

    @PostMapping("/edit/{id}")
    public String saveVehicle(@PathVariable Long id, @ModelAttribute Vehicle vehicle) {
        vehicleService.update(id, vehicle);
        return "redirect:/vehicles";
    }

    @PostMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }

    @GetMapping("/create")
    public String createVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicles/create";
    }

    @PostMapping("/create")
    public String createVehicle(@ModelAttribute Vehicle vehicle, Authentication auth) {
        User owner = userService.findByEmail(auth.getName()).orElseThrow();
        vehicle.setOwner(owner);
        vehicleService.create(vehicle);
        return "redirect:/vehicles";
    }
}
