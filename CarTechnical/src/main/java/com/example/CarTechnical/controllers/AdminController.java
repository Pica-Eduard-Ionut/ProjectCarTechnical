package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.ServiceType;
import com.example.CarTechnical.services.ServiceReportService;
import com.example.CarTechnical.services.ServiceRequestService;
import com.example.CarTechnical.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final ServiceRequestService requestService;
    private final ServiceReportService reportService;
    private final UserService userService;

    public AdminController(ServiceRequestService requestService,
                           ServiceReportService reportService,
                           UserService userService) {
        this.requestService = requestService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ServiceRequest> allRequests = requestService.findAll();

        long total = allRequests.size();
        long pending = allRequests.stream().filter(r -> r.getStatus() == ServiceStatus.PENDING).count();
        long completed = allRequests.stream().filter(r -> r.getStatus() == ServiceStatus.COMPLETED).count();

        // Determine the top service type
        String topServiceType = String.valueOf(allRequests.stream()
                .collect(Collectors.groupingBy(ServiceRequest::getServiceType, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(ServiceType.MAINTENANCE));

        // Add data to model for Thymeleaf
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRequests", total);
        stats.put("pendingCount", pending);
        stats.put("completedCount", completed);
        stats.put("topServiceType", topServiceType);

        model.addAttribute("stats", stats);
        model.addAttribute("users", userService.findAll());

        return "admin/dashboard";
    }

    @GetMapping("/requests")
    public String viewAllRequests(Model model) {
        model.addAttribute("requests", requestService.findAll());
        return "admin/requests";
    }

    @GetMapping("/reports")
    public String viewAllReports(Model model) {
        model.addAttribute("reports", reportService.findAll());
        return "admin/reports";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @PostMapping("/strategy")
    public String switchStrategy(@RequestParam String type) {
        if ("earliest".equalsIgnoreCase(type)) {
            requestService.useEarliestAvailableStrategy();
        } else {
            requestService.usePriorityBasedStrategy();
        }
        return "redirect:/requests/next";
    }
}
