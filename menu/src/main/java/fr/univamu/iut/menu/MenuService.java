package fr.univamu.iut.menu;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;


public class MenuService {
    protected MenuRepositoryInterface menuRepo;

    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
    }

    public String getAllMenusJson(){

        ArrayList<Menu> allMenus = menuRepo.getAllMenus();

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allMenus);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

        return result;
    }

    public String getMenuJSON( int id ){
        String result = null;
        Menu myMenu = menuRepo.getMenu(id);


        if( myMenu != null ) {

            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myMenu);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public boolean updateMenu(int id, Menu menu) {
        return menuRepo.updateMenu(id, menu.nom, menu.createurId, menu.createurNom, menu.dateCreation, menu.dateMiseAJour, menu.plats_id);
    }

}
