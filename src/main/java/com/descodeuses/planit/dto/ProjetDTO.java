package com.descodeuses.planit.dto;

public class ProjetDTO {
    private Long id;
    private String nomProjet;

    public ProjetDTO() {
        // Nécessaire pour la désérialisation JSON -> Objet
    }

    public ProjetDTO(Long id, String nomProjet) {
        this.id = id;
        this.nomProjet = nomProjet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

}
