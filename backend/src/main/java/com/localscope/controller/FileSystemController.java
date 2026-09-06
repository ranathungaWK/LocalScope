package com.localscope.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
public class FileSystemController {

    /**
     * Automatically scans the workspace parent directory for local projects
     * (e.g. C:\KR\work\) and returns detected project folders.
     */
    @GetMapping("/detected-projects")
    public ResponseEntity<List<Map<String, String>>> getDetectedProjects() {
        List<Map<String, String>> projects = new ArrayList<>();

        // Start by checking the parent directory of LocalScope (e.g. C:\KR\work)
        Path currentDir = Paths.get("").toAbsolutePath();
        File workspaceParent = currentDir.getParent() != null ? currentDir.getParent().toFile() : currentDir.toFile();

        if (workspaceParent.exists() && workspaceParent.isDirectory()) {
            File[] files = workspaceParent.listFiles(File::isDirectory);
            if (files != null) {
                Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                for (File file : files) {
                    if (!file.getName().startsWith(".")) {
                        Map<String, String> projectInfo = new HashMap<>();
                        projectInfo.put("name", file.getName());
                        projectInfo.put("path", file.getAbsolutePath().replace("\\", "/"));
                        projects.add(projectInfo);
                    }
                }
            }
        }

        return ResponseEntity.ok(projects);
    }

    /**
     * Browse folders on the local file system.
     */
    @GetMapping("/browse")
    public ResponseEntity<Map<String, Object>> browseDirectory(@RequestParam(required = false) String path) {
        File targetDir;
        if (path == null || path.trim().isEmpty()) {
            Path currentDir = Paths.get("").toAbsolutePath();
            targetDir = currentDir.getParent() != null ? currentDir.getParent().toFile() : currentDir.toFile();
        } else {
            targetDir = new File(path);
        }

        if (!targetDir.exists() || !targetDir.isDirectory()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Directory does not exist: " + path));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("currentPath", targetDir.getAbsolutePath().replace("\\", "/"));
        result.put("parentPath", targetDir.getParent() != null ? targetDir.getParent().replace("\\", "/") : null);

        File[] subdirs = targetDir.listFiles(File::isDirectory);
        List<Map<String, String>> dirList = new ArrayList<>();
        if (subdirs != null) {
            Arrays.sort(subdirs, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            for (File sub : subdirs) {
                if (!sub.getName().startsWith(".")) {
                    dirList.add(Map.of(
                        "name", sub.getName(),
                        "path", sub.getAbsolutePath().replace("\\", "/")
                    ));
                }
            }
        }

        result.put("directories", dirList);
        return ResponseEntity.ok(result);
    }
}
