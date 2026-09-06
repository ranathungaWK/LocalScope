package com.localscope.dto;

public class TimelineEventResponse {

    private String id;
    private String timestamp;
    private String service;
    private String type;
    private String message;
    private String severity;

    public TimelineEventResponse() {
    }

    public TimelineEventResponse(String id, String timestamp, String service, String type, String message, String severity) {
        this.id = id;
        this.timestamp = timestamp;
        this.service = service;
        this.type = type;
        this.message = message;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
