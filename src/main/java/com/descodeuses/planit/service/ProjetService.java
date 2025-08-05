package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ProjetDTO;
import com.descodeuses.planit.entity.ProjetEntity;
import com.descodeuses.planit.repository.ProjetRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjetService {
    private final ProjetRepository repository;

    public ProjetService(ProjetRepository repository) {
        this.repository = repository;
    }

    private ProjetDTO convertToDTO(ProjetEntity entity) {
        ProjetDTO dto = new ProjetDTO();
        dto.setId(entity.getId());
        dto.setNomProjet(entity.getNomProjet());
        return dto;
    }

    private ProjetEntity convertToEntity(ProjetDTO projetDTO) {
        ProjetEntity projet = new ProjetEntity();
        projet.setId(projetDTO.getId());
        projet.setNomProjet(projetDTO.getNomProjet());

        return projet;
    }

    public List<ProjetDTO> getAll() {
        List<ProjetEntity> entities = repository.findAll();
        List<ProjetDTO> dtos = new ArrayList<>();
        for (ProjetEntity item : entities) {
            dtos.add(convertToDTO(item));
        }
        return dtos;
    }

    public ProjetDTO getProjetById(Long id) {
        Optional<ProjetEntity> projet = repository.findById(id);

        if (projet.isEmpty()) {
            throw new EntityNotFoundException("Action not found with id: " + id);
        }

        return convertToDTO(projet.get());
    }

    public ProjetDTO create(ProjetDTO dto) {
        ProjetEntity projet = convertToEntity(dto);
        ProjetEntity savedProjet = repository.save(projet);
        return convertToDTO(savedProjet);
    }

    public ProjetDTO update(Long id, ProjetDTO dto) {
        ProjetEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));
        existingEntity.setNomProjet(dto.getNomProjet());
        ProjetEntity updatedEntity = repository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    public ProjetDTO delete(Long id) {
        ProjetEntity deletedEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));
        repository.deleteById(id);
        return convertToDTO(deletedEntity);
    }
}
