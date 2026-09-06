package com.localscope.controller;

import com.localscope.dto.TimelineEventResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class TimelineController {

    @GetMapping
    public ResponseEntity<List<TimelineEventResponse>> getTimelineEvents() {
        List<TimelineEventResponse> events = List.of(
            new TimelineEventResponse("e1", "10:32:01", "PostgreSQL", "STARTED", "PostgreSQL instance listening on :5432", "INFO"),
            new TimelineEventResponse("e2", "10:32:04", "Spring Boot", "STARTED", "Spring Boot backend started on :8080", "INFO"),
            new TimelineEventResponse("e3", "10:32:06", "Redis", "STARTED", "Redis cache connected on :6379", "INFO"),
            new TimelineEventResponse("e4", "10:34:18", "Redis", "STOPPED", "Redis process terminated unexpectedly (exit code 137)", "ERROR"),
            new TimelineEventResponse("e5", "10:34:21", "Spring Boot", "LATENCY_SPIKE", "GET /orders average latency increased from 35ms to 450ms", "WARN"),
            new TimelineEventResponse("e6", "10:34:23", "Spring Boot", "ERROR_500", "GET /orders returned HTTP 500 (JedisConnectionException)", "ERROR")
        );
        return ResponseEntity.ok(events);
    }
}
