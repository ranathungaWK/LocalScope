package com.localscope.dto;

import com.localscope.entity.Project;

import java.time.Instant;

public class ProjectResponse {

    private Long id;
    private String name;
    private String path;
    private Instant createdAt;

    public ProjectResponse() {
    }

    public ProjectResponse(Long id, String name, String path, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
    }

    public static ProjectResponse fromEntity(Project project) {
        return new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getPath(),
            project.getCreatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
