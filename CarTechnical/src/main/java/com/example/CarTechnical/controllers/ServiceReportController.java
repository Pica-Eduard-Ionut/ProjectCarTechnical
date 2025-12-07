package com.example.CarTechnical.controllers;

import com.example.CarTechnical.models.ServiceReport;
import com.example.CarTechnical.models.ServiceRequest;
import com.example.CarTechnical.models.ServiceStatus;
import com.example.CarTechnical.models.User;
import com.example.CarTechnical.services.ServiceReportService;
import com.example.CarTechnical.services.ServiceRequestService;
import com.example.CarTechnical.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reports")
public class ServiceReportController {

    private final ServiceReportService reportService;
    private final ServiceRequestService requestService;
    private final UserService userService;

    public ServiceReportController(ServiceReportService reportService,
                                   ServiceRequestService requestService,
                                   UserService userService) {
        this.reportService = reportService;
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping
    public String listReports(Model model, Authentication auth) {
        User user = userService.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("reports", reportService.findByMechanicId(user.getId()));
        return "reports/list";
    }

    @GetMapping("/create")
    public String createReportForm(@RequestParam(required = false) Long requestId, Model model) {

        if (requestId == null) {
            model.addAttribute("error", "Missing service request ID.");
            model.addAttribute("reports", reportService.findAll());
            return "reports/list";
        }

        model.addAttribute("requestId", requestId);
        model.addAttribute("report", new ServiceReport());
        return "reports/create";
    }

    @PostMapping("/create")
    public String createReport(@ModelAttribute ServiceReport report,
                               @RequestParam Long requestId,
                               Authentication auth) {
        User mechanic = userService.findByEmail(auth.getName()).orElseThrow();
        ServiceRequest request = requestService.findById(requestId).orElseThrow();

        report.setMechanic(mechanic);
        report.setServiceRequest(request);

        reportService.create(report);


        if ("COMPLETED".equalsIgnoreCase(report.getStatus().name())) {
            request.setStatus(ServiceStatus.COMPLETED);
            requestService.save(request);
        }

        return "redirect:/reports";
    }


    @GetMapping("/{id}")
    public String viewReport(@PathVariable Long id, Model model) {
        ServiceReport report = reportService.findById(id).orElseThrow();
        model.addAttribute("report", report);
        return "reports/view";
    }


    @PostMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportService.delete(id);
        return "redirect:/reports";
    }
}
