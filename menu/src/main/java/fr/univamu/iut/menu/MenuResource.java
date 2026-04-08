package fr.univamu.iut.menu;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/menus")
public class MenuResource {

    private MenuService service;

    public MenuResource(){}

    public @Inject MenuResource(MenuRepositoryInterface menuRepo ){
        this.service = new MenuService( menuRepo) ;
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
    public String getMenu( @PathParam("id") int id){

        String result = service.getMenuJSON(id);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @POST
    @Consumes("application/json")
    public Response createMenu(Menu menu){
        if( service.createMenu(menu) )
            return Response.ok("created").build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateMenu(@PathParam("id") int id, Menu menu ){

        if( ! service.updateMenu(id, menu) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteMenu(@PathParam("id") int id){
        if( service.deleteMenu(id) )
            return Response.ok("deleted").build();
        else
            throw new NotFoundException();
    }
}

