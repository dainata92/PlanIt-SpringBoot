package com.descodeuses.planit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.AuthRequest;
import com.descodeuses.planit.dto.AuthResponse;
import com.descodeuses.planit.entity.LogDocument;
import com.descodeuses.planit.security.JwtUtil;
import com.descodeuses.planit.service.LogDocumentService;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

	@Autowired
	private LogDocumentService logService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Map<String, Object> extras = Map.of(
            "request", request
        );
        LogDocument entry = new LogDocument();
        entry.setTimestamp(LocalDateTime.now());
        entry.setText("Login called");
        entry.setExtras(extras);
        this.logService.addLog(entry);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String role = "";
        if (auth != null) {
            for (GrantedAuthority authority : auth.getAuthorities()) { 
                role = authority.getAuthority(); 
            }
        }    

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, role));
    }
}
