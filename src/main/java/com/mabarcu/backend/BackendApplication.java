package com.mabarcu.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mabarcu")
@EntityScan(basePackages = "com.mabarcu.models")
@EnableJpaRepositories(basePackages = "com.mabarcu.backend.repositories")
public class BackendApplication { }
