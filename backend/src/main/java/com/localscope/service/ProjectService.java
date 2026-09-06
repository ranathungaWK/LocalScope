package com.localscope.service;

import com.localscope.dto.ProjectRequest;
import com.localscope.dto.ProjectResponse;
import com.localscope.entity.Project;
import com.localscope.exception.DuplicateResourceException;
import com.localscope.exception.ResourceNotFoundException;
import com.localscope.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
            .stream()
            .map(ProjectResponse::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return ProjectResponse.fromEntity(project);
    }

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        if (projectRepository.existsByPath(request.getPath())) {
            throw new DuplicateResourceException("Project already registered with path: " + request.getPath());
        }

        Project project = new Project(request.getName(), request.getPath());
        Project saved = projectRepository.save(project);
        return ProjectResponse.fromEntity(saved);
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
