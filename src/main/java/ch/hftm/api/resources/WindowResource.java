package ch.hftm.api.resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
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

import ch.hftm.api.models.Room;
import ch.hftm.api.models.Window;

@Path("/rooms/{roomId}/windows")
public class WindowResource {

    /**
     * Gets all windows in the given room
     * 
     * @param roomId
     * @return List<Window>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Window> getWindows(@PathParam("roomId") Long roomId) {
        return Room.findRoomById(roomId).windowList;
    }

    /**
     * Gets the window with the given id.
     * Only returns the window, if it is associated to the given room.
     * 
     * @param roomId
     * @param windowId
     * @return Window
     */
    @GET
    @Path("/{windowId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Window getWindow(
            @PathParam("roomId") Long roomId,
            @PathParam("windowId") Long windowId) {

        Room r = Room.findRoomById(roomId);
        Window w = Window.findWindowById(windowId);

        if (r.windowList.contains(w)) {
            return w;
        } else {
            throw new WebApplicationException("Window with id: " + w.id + " does not exist in this room", 404);
        }
    }

    /**
     * Adds a window to the given room
     * 
     * @param roomId
     * @param window
     * @return Window
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window addWindow(
            @PathParam("roomId") Long roomId,
            @Valid Window window) {

        Room r = Room.findRoomById(roomId);

        r.addWindow(window);
        return window;
    }

    /**
     * Updates window information.
     * This will only work, if the window is associated to the given room.
     * 
     * @param roomId
     * @param windowId
     * @return Window
     */
    @PUT
    @Path("/{windowId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Window updateWindow(
            @PathParam("roomId") Long roomId,
            @PathParam("windowId") Long windowId, Window window) {

        Room r = Room.findRoomById(roomId);
        Window w = Window.findWindowById(windowId);

        if (r.windowList.contains(w)) {
            w.name = window.name;
            w.description = window.description;
            w.currentState = window.currentState;
            w.desiredState = window.desiredState;
            w.weatherAware = window.weatherAware;
            return w;
        } else {
            throw new WebApplicationException("Window with id: " + w.id + " does not exist in this room", 404);
        }

    }

    /**
     * Removes the window with the given id.
     * Only works, if the window is associated to the room.
     * 
     * @param roomId
     * @param windowId
     * @return boolean
     */
    @DELETE
    @Path("/{windowId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public boolean deleteWindow(
            @PathParam("roomId") Long roomId,
            @PathParam("windowId") Long windowId) {

        Room r = Room.findRoomById(roomId);
        Window w = Window.findWindowById(windowId);

        if (r.windowList.contains(w)) {
            return r.windowList.remove(w);
        } else {
            throw new WebApplicationException("Window with id: " + w.id + " does not exist in this room", 404);
        }
    }
}