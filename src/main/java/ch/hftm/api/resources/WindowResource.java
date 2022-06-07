package ch.hftm.api.resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@Path("/windows")
public class WindowResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Window> getWindows() {
        return Window.findAllWindows();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Window getWindow(@PathParam("id") Long id) {
        return Window.findWindowById(id);
    }

    @GET
    @Path("/{id}/state")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public StateType getWindowDesiredState(@QueryParam("type") String state, @PathParam("id") Long id) {
        if (state.equals("desired")) {
            return Window.findWindowById(id).desiredState;
        } else if (state.equals("current")) {
            return Window.findWindowById(id).currentState;
        } else {
            throw new WebApplicationException("Unknown state type " + state + "does not exist.", 404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window addWindow(Window window) {
        window.persist();
        return window;
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window patchWindowState(@PathParam("id") Long id, @QueryParam("type") String type,
            @QueryParam("value") StateType value) {
        Window window = Window.findWindowById(id);
        if (type.equals("current")) {
            window.currentState = value;
        } else if (type.equals("desired")) {
            window.desiredState = value;
        }

        return window;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window updateWindow(@PathParam("id") Long id, Window window) {
        Window entity = Window.findWindowById(id);
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
        return Window.deleteWindowById(id);
    }
}
