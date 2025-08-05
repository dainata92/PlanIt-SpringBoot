package com.descodeuses.planit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.descodeuses.planit.dto.AuthRequest;
import com.descodeuses.planit.dto.UserDTO;
import com.descodeuses.planit.entity.ActionEntity;
import com.descodeuses.planit.entity.UserEntity;
import com.descodeuses.planit.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getUsername(),
                user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    private AuthRequest convertToDTO(UserEntity entity) {
        AuthRequest dto = new AuthRequest();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    public List<UserDTO> getAll() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();

        for (UserEntity user : users) {
            List<Long> actionIds = new ArrayList<>();
            if (user.getActions() != null) {
                for (ActionEntity action : user.getActions()) {
                    actionIds.add(action.getId());
                }
            }

            UserDTO dto = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    null,
                    user.getRole(),
                    actionIds);
            dtos.add(dto);
        }
        return dtos;
    }

    public UserDTO getById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Long> actionIds = new ArrayList<>();
        if (user.getActions() != null) {
            for (ActionEntity action : user.getActions()) {
                actionIds.add(action.getId());
            }
        }

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                null,
                user.getRole(),
                actionIds);
    }
}
