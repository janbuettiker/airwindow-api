package ch.hftm.api.resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import ch.hftm.api.models.Window;

@Path("/windows")
public class WindowResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Window> getWindows() {
        return Window.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Window> getWindow(@PathParam("id") Long id) {
        return Window.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window addWindow(Window window) {
        window.persist();
        return window;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window updateWindow(@PathParam("id") Long id, Window window) {
        Window entity = Window.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Entry with id of " + id + " does not exist.", 404);
        }
        entity.description = window.description;
        entity.name = window.name;
        entity.currentState = window.currentState;
        entity.desiredState = window.desiredState;
        entity.room = window.room;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public boolean deleteWindow(@PathParam("id") Long id) {
        return Window.deleteById(id);
    }
}
