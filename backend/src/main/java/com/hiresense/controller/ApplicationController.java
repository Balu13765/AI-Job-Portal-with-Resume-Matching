package com.hiresense.controller;

import com.hiresense.model.Application;
import com.hiresense.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationRepository repository;

    @GetMapping
    public List<Application> all() {
        return repository.findAll();
    }

    @PostMapping
    public Application apply(@RequestBody Application application) {
        if (application.getStatus() == null || application.getStatus().isBlank()) {
            application.setStatus("APPLIED");
        }
        return repository.save(application);
    }
}
