package com.localscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

/**
 * Main entry point for the LocalScope Backend application.
 *
 * Spring Boot Concept Guide:
 *
 * 1. @SpringBootApplication:
 *    This is a convenience meta-annotation that bundles three core Spring annotations:
 *    - @Configuration: Marks this class as a source of bean definitions for the application context.
 *    - @EnableAutoConfiguration: Tells Spring Boot to automatically configure Spring based on the
 *      jar dependencies you added in pom.xml (e.g., configuring Tomcat, SQLite, and Jackson automatically).
 *    - @ComponentScan: Tells Spring to scan the current package ('com.localscope') and all its sub-packages
 *      (like .controller, .service, .repository) to find and instantiate beans (@Component, @Service, @RestController, etc.).
 *
 * 2. @EnableScheduling:
 *    Enables Spring's background task scheduling engine. This allows any method annotated with
 *    @Scheduled(fixedRate = ...) in our service classes to run automatically in the background
 *    (essential for continuous process and port monitoring!).
 */
@SpringBootApplication
@EnableScheduling // background task manager
public class LocalScopeApplication {

    public static void main(String[] args) {
        // Ensure the local storage directory (~/.localscope) exists before SQLite tries to write to it.
        ensureLocalStorageDirectoryExists();

        // Starts the Spring application context and boots the embedded Tomcat web server.
        SpringApplication.run(LocalScopeApplication.class, args);
    }

    private static void ensureLocalStorageDirectoryExists() {
        String userHome = System.getProperty("user.home");
        File localScopeDir = new File(userHome, ".localscope");
        if (!localScopeDir.exists()) {
            boolean created = localScopeDir.mkdirs();
            if (created) {
                System.out.println("[LocalScope] Created local storage directory at: " + localScopeDir.getAbsolutePath());
            }
        }
    }
}
