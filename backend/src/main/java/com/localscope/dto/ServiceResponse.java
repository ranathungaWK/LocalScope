package com.localscope.dto;

import java.util.List;

public class ServiceResponse {

    private String id;
    private String name;
    private String type;
    private int port;
    private Integer pid;
    private String status;
    private Integer responseTimeMs;
    private List<String> dependencies;

    public ServiceResponse() {
    }

    public ServiceResponse(String id, String name, String type, int port, Integer pid, String status, Integer responseTimeMs, List<String> dependencies) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.port = port;
        this.pid = pid;
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        this.dependencies = dependencies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Integer responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }
}
