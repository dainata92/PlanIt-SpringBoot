package com.descodeuses.planit.controller;

import org.springframework.http.ResponseEntity;

import com.descodeuses.planit.dto.UserDTO;
import com.descodeuses.planit.entity.UserEntity;
import com.descodeuses.planit.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserEntity created = userService.createUser(userDTO);
        userDTO.setId(created.getId());
        userDTO.setPassword(null);
        return ResponseEntity.ok(userDTO);
    }
}
