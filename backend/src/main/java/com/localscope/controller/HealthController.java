package com.localscope.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * HealthController: A simple REST endpoint to verify that the backend is running.
 *
 * Spring Boot Concept Guide:
 *
 * 1. @RestController:
 *    Combines @Controller and @ResponseBody. It tells Spring that this class will handle
 *    incoming HTTP requests, and the return value of its methods should be serialized
 *    directly into the HTTP response body as JSON (via Jackson) instead of returning an HTML template.
 *
 * 2. @RequestMapping("/api/v1"):
 *    Sets a common URL path prefix for all endpoints in this controller.
 *    Using API versioning ('/api/v1') is standard practice for clean REST APIs.
 *
 * 3. @GetMapping("/health"):
 *    Maps HTTP GET requests targeting '/api/v1/health' to this method.
 *
 * 4. ResponseEntity<T>:
 *    Represents the entire HTTP response: status code (200 OK), headers, and the response body.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatus() {
        Map<String, Object> healthReport = Map.of(
            "status", "UP",
            "service", "LocalScope Backend",
            "version", "0.0.1-SNAPSHOT",
            "timestamp", Instant.now().toString(),
            "message", "LocalScope engine is running locally."
        );

        return ResponseEntity.ok(healthReport);
    }
}
