package fr.univamu.iut.menu;

public class Menu {

    protected int id;
    protected String nom;
    protected int createurId;
    protected String createurNom;
    protected String dateCreation;
    protected String dateMiseAJour;
    protected int plats_id;

    public Menu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id) {
        this.id = id;
        this.nom = nom;
        this.createurId = createurId;
        this.createurNom = createurNom;
        this.dateCreation = dateCreation;
        this.dateMiseAJour = dateMiseAJour;
        this.plats_id = plats_id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getCreateurId() {
        return createurId;
    }

    public String getCreateurNom() {
        return createurNom;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getDateMiseAJour() {
        return dateMiseAJour;
    }

    public int getPlats_id() {
        return plats_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCreateurId(int createurId) {
        this.createurId = createurId;
    }

    public void setCreateurNom(String createurNom) {
        this.createurNom = createurNom;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateMiseAJour(String dateMiseAJour) {
        this.dateMiseAJour = dateMiseAJour;
    }

    public void setPlats_id(int plats_id) {
        this.plats_id = plats_id;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", createurId=" + createurId +
                ", createurNom='" + createurNom + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateMiseAJour='" + dateMiseAJour + '\'' +
                ", plats_id=" + plats_id +
                '}';
    }
}
