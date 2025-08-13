package com.descodeuses.planit.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.entity.LogDocument;
import com.descodeuses.planit.entity.UserEntity;
import com.descodeuses.planit.repository.UserRepository;
import com.descodeuses.planit.service.ActionService;
import com.descodeuses.planit.service.LogDocumentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/action")
public class ActionController {

    @Autowired
    private LogDocumentService logService;

    private final ActionService service;
    private UserRepository userRepository;

    public ActionController(ActionService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionDTO> getById(@PathVariable Long id) {
        ActionDTO dto = service.getActionById(id);
        return new ResponseEntity<ActionDTO>(dto, HttpStatus.OK);
    }

   @GetMapping
    public ResponseEntity<List<ActionDTO>> getAll() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    UserEntity currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    List<ActionDTO> items = service.getAllByUser(currentUser);
    return new ResponseEntity<>(items, HttpStatus.OK);
}

   @PostMapping
public ResponseEntity<ActionDTO> create(@RequestBody ActionDTO requestDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    UserEntity currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    // Associer l'utilisateur avant la création
    requestDTO.setUserId(currentUser.getId());

    ActionDTO createdDTO = service.create(requestDTO);
    return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
}

    @PutMapping("/{id}")
public ResponseEntity<ActionDTO> update(@PathVariable Long id, @RequestBody ActionDTO requestDTO) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    UserEntity currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    ActionDTO updatedDTO = service.update(id, requestDTO, currentUser); // 👈 ici tu passes currentUser
    return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
}


   @DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    UserEntity currentUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    service.delete(id, currentUser); // 👈 ici aussi
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}


}
