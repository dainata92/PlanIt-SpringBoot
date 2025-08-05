package com.descodeuses.planit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.descodeuses.planit.dto.ProjetDTO;
import com.descodeuses.planit.service.ProjetService;

@RestController
@RequestMapping("/api/projets")
public class ProjetsController {

    private final ProjetService service;

    public ProjetsController(ProjetService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProjetDTO> getById(@PathVariable Long id) {
        ProjetDTO dto = service.getProjetById(id);
        return new ResponseEntity<ProjetDTO>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProjetDTO>> getAll() { 
        List<ProjetDTO> items = service.getAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjetDTO> create(@RequestBody ProjetDTO requestDTO) {       
        ProjetDTO createdDTO = service.create(requestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetDTO> update(@PathVariable Long id, @RequestBody ProjetDTO requestDTO) {
        ProjetDTO updatedDTO = service.update(id,requestDTO);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
