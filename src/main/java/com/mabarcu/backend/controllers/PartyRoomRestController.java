package com.mabarcu.backend.controllers;
import com.mabarcu.backend.services.AppService;
import com.mabarcu.models.PartyRoom;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/rooms") public class PartyRoomRestController { private final AppService service; public PartyRoomRestController(AppService service){this.service=service;} @GetMapping public List<PartyRoom> rooms(){return service.getRooms();} @PostMapping public PartyRoom create(@Valid @RequestBody PartyRoom room){return service.insertRoom(room);} @PostMapping("/{id}/members") public void join(@PathVariable int id,@RequestBody Map<String,String> b){service.addRoomMember(id,b.get("name"));} @DeleteMapping("/{id}/members") public void leave(@PathVariable int id,@RequestParam String name){service.removeRoomMember(id,name);} }
