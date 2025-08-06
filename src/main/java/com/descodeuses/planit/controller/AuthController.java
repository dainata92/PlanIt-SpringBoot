package com.descodeuses.planit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.AuthRequest;
import com.descodeuses.planit.dto.AuthResponse;
import com.descodeuses.planit.security.JwtUtil;
import com.descodeuses.planit.service.LogDocumentService;
import com.descodeuses.planit.service.UserDetailsServiceImpl;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl service;
    private final LogDocumentService serviceLog;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserDetailsServiceImpl service,
            LogDocumentService serviceLog,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {
        this.service = service;
        this.serviceLog = serviceLog;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
    return ResponseEntity.ok("Auth test OK");
}
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Map<String, Object> extras = Map.of("request", request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtUtil.generateToken(request.getUsername());
        serviceLog.addLog("login called", LocalDate.now(), request.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
