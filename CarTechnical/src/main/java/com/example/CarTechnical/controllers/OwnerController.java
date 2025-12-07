package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.User;
import com.example.CarTechnical.services.ServiceRequestService;
import com.example.CarTechnical.services.ServiceReportService;
import com.example.CarTechnical.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@PreAuthorize("hasRole('OWNER')")
@RequestMapping("/owner")
public class OwnerController {

    private final ServiceRequestService requestService;
    private final ServiceReportService reportService;
    private final UserService userService;

    public OwnerController(ServiceRequestService requestService,
                           ServiceReportService reportService,
                           UserService userService) {
        this.requestService = requestService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return "redirect:/login";
        }

        User owner = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + auth.getName()));

        List<ServiceRequest> recentRequests = requestService.findByOwnerId(owner.getId());

        model.addAttribute("user", owner);
        model.addAttribute("recentRequests", recentRequests);

        return "owner/dashboard";
    }
}