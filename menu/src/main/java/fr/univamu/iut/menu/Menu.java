package fr.univamu.iut.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    protected int id;
    protected String nom;
    protected int createurId;
    protected String createurNom;
    protected String dateCreation;
    protected String dateMiseAJour;
    protected double prix_total;
    protected List<Integer> plats_ids;

    public Menu() {
        this.plats_ids = new ArrayList<>();
    }

    public Menu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, double prix_total, List<Integer> plats_ids) {
        this.id = id;
        this.nom = nom;
        this.createurId = createurId;
        this.createurNom = createurNom;
        this.dateCreation = dateCreation;
        this.dateMiseAJour = dateMiseAJour;
        this.prix_total = prix_total;
        this.plats_ids = plats_ids;
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

    public double getPrix_total() {
        return prix_total;
    }

    public List<Integer> getPlats_ids() {
        return plats_ids;
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

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public void setPlats_ids(List<Integer> plats_ids) {
        this.plats_ids = plats_ids;
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
                ", prix_total=" + prix_total +
                ", plats_ids=" + plats_ids +
                '}';
    }
}
