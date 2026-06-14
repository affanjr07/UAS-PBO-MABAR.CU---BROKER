package com.mabarcu.backend.controllers;
import com.mabarcu.backend.services.AppService;
import com.mabarcu.models.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/users") public class UserController { private final AppService service; public UserController(AppService service){this.service=service;} @GetMapping public List<User> players(){return service.getPlayers();} @PutMapping("/{id}") public User update(@PathVariable int id,@Valid @RequestBody User user){ user.setId(id); return service.updateProfile(user);} @GetMapping("/{username}") public User detail(@PathVariable String username){return service.findUser(username);} }
