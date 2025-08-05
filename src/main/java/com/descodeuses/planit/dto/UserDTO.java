package com.descodeuses.planit.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private List<Long> actionIds;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, String role, List<Long> actionIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.actionIds = actionIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getActionIds() {
        return actionIds;
    }

    public void setActionIds(List<Long> actionIds) {
        this.actionIds = actionIds;
    }

}
