package com.mabarcu.backend.controllers;
import com.mabarcu.backend.services.AppService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/chat") public class ChatRestController { private final AppService service; public ChatRestController(AppService service){this.service=service;} @GetMapping public List<String> messages(){return service.getChat();} @PostMapping public void send(@RequestBody Map<String,String> b){service.addChat(b.get("sender"),b.get("message"));} }
