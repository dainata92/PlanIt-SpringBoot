package com.descodeuses.planit.controller;

import com.descodeuses.planit.dto.UserDTO;

import com.descodeuses.planit.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class UtilisateurController {

    private final UserDetailsServiceImpl userDetailsService;

    public UtilisateurController( UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAll()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userDetailsService.getById(id)); 
    }
}

