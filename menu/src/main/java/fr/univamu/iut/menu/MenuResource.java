package fr.univamu.iut.menu;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public class MenuResource {

    private MenuService service;

    public MenuResource(){}

    public MenuResource(MenuRepositoryInterface bookRepo ){
        this.service = new MenuService( bookRepo) ;
    }

    public MenuResource (MenuService service){
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllMenus() {
        return service.getAllMenusJson();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getBook( @PathParam("id") int id){

        String result = service.getMenuJSON(id);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateMenu(@PathParam("id") int id, Menu nom ){

        if( ! service.updateMenu(id, nom) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
}

