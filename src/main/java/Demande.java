

class Demande {
    private int idDemande;
    private String typeAide;
    private String description;
    private String statut;

    public Demande(int idDemande, String typeAide, String description, String statut) {
        this.idDemande = idDemande;
        this.typeAide = typeAide;
        this.description = description;
        this.statut = statut;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public String getTypeAide() {
        return typeAide;
    }

    public String getDescription() {
        return description;
    }

    public String getStatut() {
        return statut;
    }
}
