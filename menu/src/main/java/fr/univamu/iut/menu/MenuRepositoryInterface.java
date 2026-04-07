package fr.univamu.iut.menu;

import java.util.*;

public interface MenuRepositoryInterface {
    public void close();
    public Menu getMenu(int id);
    public ArrayList<Menu> getAllMenus();
    public Menu createMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id);
    public boolean deleteMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id);
    public boolean updateMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id);

}
