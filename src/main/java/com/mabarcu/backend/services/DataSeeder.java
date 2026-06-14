package com.mabarcu.backend.services;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component public class DataSeeder implements CommandLineRunner { private final AppService service; public DataSeeder(AppService service){this.service=service;} @Override public void run(String... args){ service.seed(); } }
