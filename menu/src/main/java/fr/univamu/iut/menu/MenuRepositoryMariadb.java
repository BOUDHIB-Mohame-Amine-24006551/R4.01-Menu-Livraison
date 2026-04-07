package fr.univamu.iut.menu;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;


public class MenuRepositoryMariadb {

    protected Connection dbConnection;

    public MenuRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Menu getMenu(int id) {
        Menu selectedMenu = null;

        String query = "SELECT * FROM MENU WHERE MENU_ID = ?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();

            if ( result.next()) {
                String nom = result.getString("")
            }
        }
    }



}
