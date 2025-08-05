package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.ContactDTO;
import com.descodeuses.planit.entity.ContactEntity;
import com.descodeuses.planit.repository.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    private ContactDTO convertToDTO(ContactEntity entity) {
        ContactDTO dto = new ContactDTO();
        dto.setId(entity.getId());
        dto.setPrenom(entity.getPrenom());
        return dto;
    }

    private ContactEntity convertToEntity(ContactDTO actionDTO) {
        ContactEntity action = new ContactEntity();
        action.setId(actionDTO.getId());
        action.setPrenom(actionDTO.getPrenom());

        return action;
    }

    public List<ContactDTO> getAll() {
        List<ContactEntity> entities = repository.findAll();
        // Declarer une variable liste de action DTO
        List<ContactDTO> dtos = new ArrayList<>();

        // Faire boucle sur la liste action
        for (ContactEntity item : entities) {
            // Convertir action vers action dto
            // Ajouter action dto dans la liste
            dtos.add(convertToDTO(item));
        }
        return dtos;
    }

    public ContactDTO getContactById(Long id) {
        // Version courte
        /*
         * Action action =
         * repository
         * .findById(id)
         * .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " +
         * id));
         * 
         * return convertToDTO(action);
         */

        // Version longue explicite
        Optional<ContactEntity> action = repository.findById(id);

        if (action.isEmpty()) {
            throw new EntityNotFoundException("Action not found with id: " + id);
        }

        return convertToDTO(action.get());
    }

    public ContactDTO create(ContactDTO dto) {
        // Convertir DTO en entité
        ContactEntity action = convertToEntity(dto);
        // Sauvegarder l'entité
        ContactEntity savedAction = repository.save(action);
        // Convertir l'entité enregistrée en DTO et retourner
        // retourner convertirVersDTO(entitéEnregistrée)
        return convertToDTO(savedAction);
    }

    public ContactDTO update(Long id, ContactDTO dto) {
        // Rechercher l'entité par son identifiant
        // sinon lever une exception "Ressource non trouvée"
        ContactEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));

        // Mettre à jour les champs de l'entité avec les valeurs du DTO
        existingEntity.setPrenom(dto.getPrenom());

        // Sauvegarder l'entité mise à jour dans la base de données
        ContactEntity updatedEntity = repository.save(existingEntity);
        // Convertir l'entité mise à jour en DTO et retourner
        // retourner convertirVersDTO(entitéMiseAJour)
        return convertToDTO(updatedEntity);
    }

    public ContactDTO delete(Long id) {
        // Vérifier si l'entité existe
        ContactEntity deletedEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));

        // Supprimer l'entité
        repository.deleteById(id);

        // Retourner l'entité supprimée sous forme de DTO (avant suppression)
        return convertToDTO(deletedEntity);
    }
}
