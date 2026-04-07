package fr.univamu.iut.menu;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

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
        String query = "SELECT * FROM Menu WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if ( result.next() ) {
                String nom = result.getString("nom");
                int createurId = result.getInt("createurId");
                String createurNom = result.getString("createurNom");
                String dateCreation = result.getString("dateCreation");
                String dateMiseAJour = result.getString("dateMiseAJour");
                int plats_id = result.getInt("plats_id");

                selectedMenu = new Menu(id, nom, createurId, createurNom, dateCreation, dateMiseAJour, plats_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedMenu;
    }

    @Override
    public ArrayList<Menu> getAllMenus() {
        ArrayList<Menu> listMenus = new ArrayList<>();
        String query = "SELECT * FROM Menu";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();

            while ( result.next() ) {
                int id = result.getInt("id");
                String nom = result.getString("nom");
                int createurId = result.getInt("createurId");
                String createurNom = result.getString("createurNom");
                String dateCreation = result.getString("dateCreation");
                String dateMiseAJour = result.getString("dateMiseAJour");
                int plats_id = result.getInt("plats_id");

                Menu currentMenu = new Menu(id, nom, createurId, createurNom, dateCreation, dateMiseAJour, plats_id);
                listMenus.add(currentMenu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMenus;
    }

    @Override
    public Menu createMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id) {
        String query = "INSERT INTO Menu (id, nom, createurId, createurNom, dateCreation, dateMiseAJour, plats_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setInt(3, createurId);
            ps.setString(4, createurNom);
            ps.setString(5, dateCreation);
            ps.setString(6, dateMiseAJour);
            ps.setInt(7, plats_id);

            int nbRowModified = ps.executeUpdate();
            if (nbRowModified > 0) {
                return new Menu(id, nom, createurId, createurNom, dateCreation, dateMiseAJour, plats_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id) {
        String query = "DELETE FROM Menu WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            int nbRowModified = ps.executeUpdate();
            return ( nbRowModified != 0 );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMenu(int id, String nom, int createurId, String createurNom, String dateCreation, String dateMiseAJour, int plats_id) {
        String query = "UPDATE Menu SET nom=?, createurId=?, createurNom=?, dateCreation=?, dateMiseAJour=?, plats_id=? WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, nom);
            ps.setInt(2, createurId);
            ps.setString(3, createurNom);
            ps.setString(4, dateCreation);
            ps.setString(5, dateMiseAJour);
            ps.setInt(6, plats_id);
            ps.setInt(7, id);

            int nbRowModified = ps.executeUpdate();
            return ( nbRowModified != 0 );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
