package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.ServiceReport;
import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.User;
import com.example.CarTechnical.repositories.ServiceRequestRepository;
import com.example.CarTechnical.services.ServiceReportService;
import com.example.CarTechnical.services.ServiceRequestService;
import com.example.CarTechnical.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@PreAuthorize("hasRole('MECHANIC')")
@RequestMapping("/mechanic")
public class MechanicController {

    private final UserService userService;
    private final ServiceRequestService requestService;
    private final ServiceReportService reportService;
    private final ServiceRequestRepository requestRepository;

    public MechanicController(UserService userService, ServiceRequestService requestService, ServiceReportService reportService, ServiceRequestRepository requestRepository) {
        this.userService = userService;
        this.requestService = requestService;
        this.reportService = reportService;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User mechanic = userService.findByEmail(auth.getName()).orElseThrow();
        List<ServiceRequest> assigned = requestService.findByMechanic(mechanic);
        model.addAttribute("assignedRequests", assigned);
        return "mechanic/dashboard";
    }

    @GetMapping("/request/{id}")
    public String viewAssignedRequest(@PathVariable Long id, Model model) {
        model.addAttribute("request", requestService.findById(id).orElseThrow());
        return "mechanic/request-view";
    }

    @GetMapping("/request/edit/{id}")
    public String editRequestForm(@PathVariable Long id, Model model) {
        ServiceRequest request = requestService.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        model.addAttribute("request", request);
        model.addAttribute("statuses", ServiceStatus.values());
        return "mechanic/edit-request";
    }

    @PostMapping("/request/edit/{id}")
    public String handleEditRequest(
            @PathVariable Long id,
            @RequestParam ServiceStatus status,
            @RequestParam String mechanicNotes,
            @RequestParam double totalCost,
            @RequestParam(required = false) String partsUsed) {

        updateMechanicRequest(id, status, mechanicNotes, totalCost, partsUsed);
        return "redirect:/mechanic/dashboard";
    }

    public void updateMechanicRequest(Long requestId,
                                      ServiceStatus status,
                                      String mechanicNotes,
                                      double totalCost,
                                      String partsUsed) {
        ServiceRequest req = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        req.setStatus(status);

        ServiceReport report = req.getReport();
        if (report == null) {
            report = new ServiceReport();
            report.setServiceRequest(req);
            report.setMechanic(req.getAssignedMechanic());
        }

        report.setPerformedAt(LocalDateTime.now());
        report.setSummary(mechanicNotes);
        report.setPartsUsed(partsUsed != null ? partsUsed : "");
        report.setTotalCost(totalCost);

        req.setReport(report);

        requestRepository.save(req);
    }
}
