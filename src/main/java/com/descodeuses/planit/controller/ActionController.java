package com.descodeuses.planit.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.entity.LogDocument;
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

    public ActionController(ActionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionDTO> getById(@PathVariable Long id) {
        ActionDTO dto = service.getActionById(id);
        return new ResponseEntity<ActionDTO>(dto, HttpStatus.OK);
    }

    @GetMapping
    // public List<ActionDTO> getAll() {
    public ResponseEntity<List<ActionDTO>> getAll() {
        List<ActionDTO> items = service.getAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActionDTO> create(@RequestBody ActionDTO requestDTO) {
        Map<String, Object> extras = Map.of(
            "request", requestDTO
        );
        LogDocument entry = new LogDocument();
        entry.setTimestamp(LocalDateTime.now());
        entry.setText("Todo called");
        entry.setExtras(extras);
        this.logService.addLog(entry);
        ActionDTO createdDTO = service.create(requestDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActionDTO> update(@PathVariable Long id, @RequestBody ActionDTO requestDTO) {
        ActionDTO updatedDTO = service.update(id, requestDTO);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
