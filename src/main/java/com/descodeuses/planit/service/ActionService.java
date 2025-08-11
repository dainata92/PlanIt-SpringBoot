package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.descodeuses.planit.dto.ActionDTO;
import com.descodeuses.planit.dto.ContactDTO;
import com.descodeuses.planit.dto.ProjetDTO;
import com.descodeuses.planit.dto.UserDTO;
import com.descodeuses.planit.entity.ActionEntity;
import com.descodeuses.planit.entity.ContactEntity;
import com.descodeuses.planit.entity.ProjetEntity;
import com.descodeuses.planit.entity.UserEntity;
import com.descodeuses.planit.exceptions.ResourceNotFoundException;
import com.descodeuses.planit.repository.ActionRepository;
import com.descodeuses.planit.repository.ContactRepository;
import com.descodeuses.planit.repository.ProjetRepository;
import com.descodeuses.planit.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActionService {

    private final ActionRepository repository;
    private final ContactRepository contactRepository;
    private final ProjetRepository projetRepository;
    private final UserRepository userRepository;

    public ActionService(ActionRepository repository, ContactRepository contactRepository,
            ProjetRepository projetRepository, UserRepository userRepository) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.projetRepository = projetRepository;
        this.userRepository = userRepository;
    }

    private ActionDTO convertToDTO(ActionEntity action) {
        ActionDTO dto = new ActionDTO(
                action.getId(),
                action.getTitle(),
                action.getCompleted(),
                action.getDueDate(),
                action.getPriority(),
                action.getDescription());

        // Convertir les membres vers ContactDTO
        List<ContactDTO> membresDTO = new ArrayList<>();
        if (action.getMembres() != null) {
            for (ContactEntity contact : action.getMembres()) {
                ContactDTO contactDTO = new ContactDTO(contact.getId(), contact.getPrenom());
                membresDTO.add(contactDTO);
            }
        }
        dto.setMembres(membresDTO);

        if (action.getProjet() != null) {
            ProjetDTO projetDTO = new ProjetDTO(action.getProjet().getId(), action.getProjet().getNomProjet());
            dto.setProjet(projetDTO);
        }
        if (action.getUser() != null) {
            UserEntity userEntity = action.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(userEntity.getId());

            dto.setUser(userDTO);
            dto.setUserId(userEntity.getId());
            // dto.setUserId(action.getUser().getId());
        }

        return dto;
    }

    private ActionEntity convertToEntity(ActionDTO dto) {
        ActionEntity entity = new ActionEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.getCompleted());
        entity.setDueDate(dto.getDueDate());
        entity.setPriority(dto.getPriority());
        entity.setDescription(dto.getDescription());

        // Récupérer les ContactEntity depuis les id
        Set<ContactEntity> membres = new HashSet<>();
        if (dto.getMembres() != null) {
            for (ContactDTO contactDTO : dto.getMembres()) {
                ContactEntity contact = contactRepository.findById(contactDTO.getId())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Contact not found with id: " + contactDTO.getId()));
                membres.add(contact);
            }
        }
        entity.setMembres(membres);

        if (dto.getProjet() != null && dto.getProjet().getId() != null) {
            ProjetEntity projet = projetRepository.findById(dto.getProjet().getId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Projet not found with id: " + dto.getProjet().getId()));
            entity.setProjet(projet);
        }

        if (dto.getUserId() != null) {
            UserEntity user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));
            entity.setUser(user);
        }

        return entity;
    }

    public List<ActionDTO> getAll() {
        List<ActionEntity> entities = repository.findAll();
        // Declarer une variable liste de action DTO
        List<ActionDTO> dtos = new ArrayList<>();

        // Faire boucle sur la liste action
        for (ActionEntity item : entities) {
            // Convertir action vers action dto
            // Ajouter action dto dans la liste
            dtos.add(convertToDTO(item));
        }
        return dtos;
    }

    public ActionDTO getActionById(Long id) {
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
        Optional<ActionEntity> action = repository.findById(id);

        if (action.isEmpty()) {
            throw new EntityNotFoundException("Action not found with id: " + id);
        }

        return convertToDTO(action.get());
    }

    public ActionDTO create(ActionDTO dto) {
        // Convertir DTO en entité
        ActionEntity action = convertToEntity(dto);
        // removeDuplicateMembres(action);
        // Sauvegarder l'entité
        ActionEntity savedAction = repository.save(action);
        // Convertir l'entité enregistrée en DTO et retourner
        // retourner convertirVersDTO(entitéEnregistrée)
        return convertToDTO(savedAction);
    }

    public ActionDTO update(Long id, ActionDTO dto) {
        ActionEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));

        existingEntity.setTitle(dto.getTitle());
        existingEntity.setCompleted(dto.getCompleted());
        existingEntity.setDueDate(dto.getDueDate());
        existingEntity.setPriority(dto.getPriority());
        existingEntity.setDescription(dto.getDescription());

        // MAJ des membres
        if (dto.getMembres() != null) {
            existingEntity.getMembres().clear();
            for (ContactDTO contactDTO : dto.getMembres()) {
                ContactEntity contact = contactRepository.findById(contactDTO.getId())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Contact not found with id: " + contactDTO.getId()));
                existingEntity.getMembres().add(contact);
            }
        }

        // MAJ du projet
        if (dto.getProjet() != null && dto.getProjet().getId() != null) {
            ProjetEntity projet = projetRepository.findById(dto.getProjet().getId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Projet not found with id: " + dto.getProjet().getId()));
            existingEntity.setProjet(projet);
        } else {
            // si projet non défini dans DTO, on supprime la relation
            existingEntity.setProjet(null);
        }

        if (dto.getUserId() != null) {
            UserEntity user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));
            existingEntity.setUser(user);
        } else {
            existingEntity.setUser(null);
        }
        System.out.println(
                "Avant save - userId: " + (existingEntity.getUser() != null ? existingEntity.getUser().getId() : null));

        ActionEntity updatedEntity = repository.save(existingEntity);

        System.out.println(
                "Après save - userId: " + (updatedEntity.getUser() != null ? updatedEntity.getUser().getId() : null));

        return convertToDTO(updatedEntity);
    }

    public void delete(Long id) {
        // Vérifier si l'entité existe
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
