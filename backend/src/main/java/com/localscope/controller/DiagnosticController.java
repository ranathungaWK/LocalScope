package com.localscope.controller;

import com.localscope.dto.DiagnosticResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diagnostics")
public class DiagnosticController {

    @GetMapping
    public ResponseEntity<List<DiagnosticResponse>> getDiagnosticFindings() {
        List<DiagnosticResponse> diagnostics = List.of(
            new DiagnosticResponse(
                "d1",
                "Redis Outage Correlated with Backend Failure",
                "Spring Boot Backend",
                "GET /orders",
                "Redis Cache (:6379)",
                3.8,
                "HIGH",
                "Redis became unavailable 3.8 seconds before /orders returned 500 errors. Spring Boot dependency tree indicates direct cache dependency.",
                "10:34:23"
            )
        );
        return ResponseEntity.ok(diagnostics);
    }
}
