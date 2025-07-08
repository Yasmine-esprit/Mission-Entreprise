package tn.esprit.spring.missionentreprise.Entities;

public enum TypePieceJointe {
    FICHIER("Fichier"),
    LIEN("Lien");

    private final String displayName;

    TypePieceJointe(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
