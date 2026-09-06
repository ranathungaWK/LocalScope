package com.localscope.dto;

public class DiagnosticResponse {

    private String id;
    private String title;
    private String affectedService;
    private String affectedEndpoint;
    private String rootCauseService;
    private double timeDeltaSeconds;
    private String confidence;
    private String description;
    private String timestamp;

    public DiagnosticResponse() {
    }

    public DiagnosticResponse(String id, String title, String affectedService, String affectedEndpoint, String rootCauseService, double timeDeltaSeconds, String confidence, String description, String timestamp) {
        this.id = id;
        this.title = title;
        this.affectedService = affectedService;
        this.affectedEndpoint = affectedEndpoint;
        this.rootCauseService = rootCauseService;
        this.timeDeltaSeconds = timeDeltaSeconds;
        this.confidence = confidence;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAffectedService() {
        return affectedService;
    }

    public void setAffectedService(String affectedService) {
        this.affectedService = affectedService;
    }

    public String getAffectedEndpoint() {
        return affectedEndpoint;
    }

    public void setAffectedEndpoint(String affectedEndpoint) {
        this.affectedEndpoint = affectedEndpoint;
    }

    public String getRootCauseService() {
        return rootCauseService;
    }

    public void setRootCauseService(String rootCauseService) {
        this.rootCauseService = rootCauseService;
    }

    public double getTimeDeltaSeconds() {
        return timeDeltaSeconds;
    }

    public void setTimeDeltaSeconds(double timeDeltaSeconds) {
        this.timeDeltaSeconds = timeDeltaSeconds;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
