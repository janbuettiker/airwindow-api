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

import ch.hftm.api.models.Room;

@Path("/rooms")
public class RoomResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return Room.findAllRooms();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") Long id) {
        return Room.findRoomById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Room addRoom(Room room) {
        room.persist();
        return room;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Room updateRoom(@PathParam("id") Long id, Room room) {
        Room entity = Room.findRoomById(id);
        if (entity == null) {
            throw new WebApplicationException("Entry with id of " + id + " does not exist.", 404);
        }
        entity.description = room.description;
        entity.name = room.name;
        entity.home = room.home;
        entity.windowList = room.windowList;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public boolean deleteRoom(@PathParam("id") Long id) {
        return Room.deleteRoomById(id);
    }

}
