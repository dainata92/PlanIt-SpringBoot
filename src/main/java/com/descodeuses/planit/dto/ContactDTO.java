package com.descodeuses.planit.dto;

public class ContactDTO {

    private Long id;
    private String prenom;

    public ContactDTO() {
        // Nécessaire pour la désérialisation JSON -> Objet
    }

    public ContactDTO(Long id, String prenom) {
        this.id = id;
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
