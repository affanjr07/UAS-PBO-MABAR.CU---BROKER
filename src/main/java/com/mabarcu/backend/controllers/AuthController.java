package com.mabarcu.backend.controllers;
import com.mabarcu.backend.services.AppService;
import com.mabarcu.models.User;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController { private final AppService service; public AuthController(AppService service){this.service=service;} @PostMapping("/login") public User login(@RequestBody Map<String,String> body){ return service.loginOrCreate(body.get("username"), body.get("password")); } @PostMapping("/register") public User register(@RequestBody Map<String,String> body){ return service.loginOrCreate(body.get("username"), body.get("password")); }}
