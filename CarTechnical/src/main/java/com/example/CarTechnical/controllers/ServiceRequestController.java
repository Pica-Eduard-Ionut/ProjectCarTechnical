package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.*;
import com.example.CarTechnical.services.ServiceRequestService;
import com.example.CarTechnical.services.UserService;
import com.example.CarTechnical.services.VehicleService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/requests")
public class ServiceRequestController {

    private final ServiceRequestService requestService;
    private final UserService userService;
    private final VehicleService vehicleService;

    public ServiceRequestController(ServiceRequestService requestService,
                                    UserService userService,
                                    VehicleService vehicleService) {
        this.requestService = requestService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/create")
    public String createRequestForm(Model model, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("vehicles", vehicleService.findByOwnerId(user.getId()));
        model.addAttribute("request", new ServiceRequest());
        return "requests/create";
    }

    @PostMapping("/create")
    public String createRequest(@ModelAttribute ServiceRequest request, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        request.setOwner(user);
        requestService.create(request);
        return "redirect:/requests";
    }

    @GetMapping("/assign/{id}")
    public String assignRequestForm(@PathVariable Long id, Model model) {
        model.addAttribute("requestId", id);
        model.addAttribute("mechanics", userService.findAllMechanics());
        return "requests/assign";
    }

    @PostMapping("/cancel/{id}")
    public String cancelRequest(@PathVariable Long id, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();

        ServiceRequest request = requestService.findById(id).orElseThrow();
        if (!request.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized to cancel this request");
        }

        requestService.updateStatus(id, ServiceStatus.CANCELLED);
        return "redirect:/requests";
    }

    @PostMapping("/assign/{id}")
    public String assignRequest(@PathVariable Long id, @RequestParam Long mechanicId) {
        requestService.assignMechanic(id, mechanicId);
        return "redirect:/requests";
    }

    @GetMapping("/next")
    public String showNextScheduledRequest(Model model) {
        ServiceRequest nextRequest = requestService.getNextScheduledRequest();
        model.addAttribute("nextRequest", nextRequest);

        List<User> mechanics = userService.findAllMechanics();
        model.addAttribute("mechanics", mechanics);

        return "requests/next";
    }


    @GetMapping("/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        ServiceRequest req = requestService.findById(id).orElseThrow();
        model.addAttribute("request", req);
        return "requests/view";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam ServiceStatus status) {
        requestService.updateStatus(id, status);
        return "redirect:/requests/" + id;
    }

    @GetMapping
    public String listRequests(Model model, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();

        if (user.getRole() == Role.ADMIN) {
            model.addAttribute("requests", requestService.findAll());
        } else {
            model.addAttribute("requests",
                    requestService.findByOwnerId(user.getId())
                            .stream()
                            .filter(r -> r.getStatus() != ServiceStatus.CANCELLED
                                    && r.getStatus() != ServiceStatus.COMPLETED)
                            .collect(Collectors.toList()));
        }

        return "requests/list";
    }

}
