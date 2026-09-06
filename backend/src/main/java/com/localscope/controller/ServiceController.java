package com.localscope.controller;

import com.localscope.dto.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getMonitoredServices() {
        List<ServiceResponse> services = List.of(
            new ServiceResponse("1", "React Frontend", "Node/Vite", 3000, 14208, "RUNNING", 12, List.of("Spring Boot Backend")),
            new ServiceResponse("2", "Spring Boot Backend", "Java/Spring", 8080, 19844, "RUNNING", 25, List.of("PostgreSQL", "Redis", "Python ML API")),
            new ServiceResponse("3", "PostgreSQL Database", "Database", 5432, 4892, "RUNNING", 8, List.of()),
            new ServiceResponse("4", "Redis Cache", "Cache/Store", 6379, 2190, "DOWN", null, List.of()),
            new ServiceResponse("5", "Python ML API", "FastAPI/Uvicorn", 8000, 8740, "WARNING", 820, List.of())
        );
        return ResponseEntity.ok(services);
    }
}
