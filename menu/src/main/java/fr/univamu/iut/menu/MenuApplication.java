package fr.univamu.iut.menu;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;


@ApplicationPath("/api")
@ApplicationScoped
public class MenuApplication extends Application {
    @Produces
    private MenuRepositoryInterface openDbConnection(){
        MenuRepositoryMariadb db = null;

        try{
            db = new MenuRepositoryMariadb("jdbc:mariadb://mysql-boudhib.alwaysdata.net/boudhib_menu_projet", "boudhib", "bkmha590");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes MenuRepositoryInterface menuRepo ) {
        menuRepo.close();
    }
}