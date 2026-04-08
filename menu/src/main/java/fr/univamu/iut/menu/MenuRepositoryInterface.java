package fr.univamu.iut.menu;

import java.util.*;

public interface MenuRepositoryInterface {
    public void close();
    public Menu getMenu(int id);
    public ArrayList<Menu> getAllMenus();
    public Menu createMenu(Menu menu);
    public boolean deleteMenu(int id);
    public boolean updateMenu(int id, Menu menu);
}
