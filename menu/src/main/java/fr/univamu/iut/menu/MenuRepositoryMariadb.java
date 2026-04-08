package fr.univamu.iut.menu;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'accéder aux menus stockés dans une base de données Mariadb
 */
public class MenuRepositoryMariadb implements MenuRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public MenuRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Menu getMenu(int id) {
        Menu selectedMenu = null;
        String query = "SELECT * FROM menus WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if ( result.next() ) {
                String nom = result.getString("nom");
                int createurId = result.getInt("createurId");
                String createurNom = result.getString("createurNom");
                String dateCreation = result.getString("dateCreation");
                String dateMiseAJour = result.getString("dateMiseAJour");
                double prix_total = result.getDouble("prix_total");

                List<Integer> plats_ids = getPlatsIdsForMenu(id);
                selectedMenu = new Menu(id, nom, createurId, createurNom, dateCreation, dateMiseAJour, prix_total, plats_ids);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }

    private List<Integer> getPlatsIdsForMenu(int menuId) throws SQLException {
        List<Integer> plats_ids = new ArrayList<>();
        String query = "SELECT plat_id FROM plats_ligne WHERE menu_id=?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, menuId);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                plats_ids.add(result.getInt("plat_id"));
            }
        }
        return plats_ids;
    }

    @Override
    public ArrayList<Menu> getAllMenus() {
        ArrayList<Menu> listMenus = new ArrayList<>();
        String query = "SELECT * FROM menus";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();

            while ( result.next() ) {
                int id = result.getInt("id");
                String nom = result.getString("nom");
                int createurId = result.getInt("createurId");
                String createurNom = result.getString("createurNom");
                String dateCreation = result.getString("dateCreation");
                String dateMiseAJour = result.getString("dateMiseAJour");
                double prix_total = result.getDouble("prix_total");

                List<Integer> plats_ids = getPlatsIdsForMenu(id);
                Menu currentMenu = new Menu(id, nom, createurId, createurNom, dateCreation, dateMiseAJour, prix_total, plats_ids);
                listMenus.add(currentMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenus;
    }

    @Override
    public Menu createMenu(Menu menu) {
        String queryMenu = "INSERT INTO menus (id, nom, createurId, createurNom, dateCreation, dateMiseAJour, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.setAutoCommit(false);
            try ( PreparedStatement ps = dbConnection.prepareStatement(queryMenu) ){
                ps.setInt(1, menu.getId());
                ps.setString(2, menu.getNom());
                ps.setInt(3, menu.getCreateurId());
                ps.setString(4, menu.getCreateurNom());
                ps.setString(5, menu.getDateCreation());
                ps.setString(6, menu.getDateMiseAJour());
                ps.setDouble(7, menu.getPrix_total());

                ps.executeUpdate();
                
                insertPlatsLignes(menu.getId(), menu.getPlats_ids());
                
                dbConnection.commit();
                return menu;
            } catch (SQLException e) {
                dbConnection.rollback();
                throw e;
            } finally {
                dbConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertPlatsLignes(int menuId, List<Integer> plats_ids) throws SQLException {
        if (plats_ids == null || plats_ids.isEmpty()) return;
        String queryLignes = "INSERT INTO plats_ligne (menu_id, plat_id) VALUES (?, ?)";
        try (PreparedStatement ps = dbConnection.prepareStatement(queryLignes)) {
            for (int platId : plats_ids) {
                ps.setInt(1, menuId);
                ps.setInt(2, platId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public boolean deleteMenu(int id) {
        String query = "DELETE FROM menus WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            int nbRowModified = ps.executeUpdate();
            return ( nbRowModified != 0 );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMenu(int id, Menu menu) {
        String query = "UPDATE menus SET nom=?, createurId=?, createurNom=?, dateCreation=?, dateMiseAJour=?, prix_total=? WHERE id=?";

        try {
            dbConnection.setAutoCommit(false);
            try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
                ps.setString(1, menu.getNom());
                ps.setInt(2, menu.getCreateurId());
                ps.setString(3, menu.getCreateurNom());
                ps.setString(4, menu.getDateCreation());
                ps.setString(5, menu.getDateMiseAJour());
                ps.setDouble(6, menu.getPrix_total());
                ps.setInt(7, id);

                int nbRowModified = ps.executeUpdate();
                
                // Sync plats_ligne
                String deleteLignes = "DELETE FROM plats_ligne WHERE menu_id=?";
                try (PreparedStatement psDelete = dbConnection.prepareStatement(deleteLignes)) {
                    psDelete.setInt(1, id);
                    psDelete.executeUpdate();
                }
                insertPlatsLignes(id, menu.getPlats_ids());

                dbConnection.commit();
                return ( nbRowModified != 0 );
            } catch (SQLException e) {
                dbConnection.rollback();
                throw e;
            } finally {
                dbConnection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
