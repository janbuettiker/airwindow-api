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

import ch.hftm.api.models.Home;
import ch.hftm.api.models.Room;

@Path("homes/{homeId}/rooms")
public class RoomResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms(@PathParam("homeId") Long homeID) {
        return Home.findHomeById(homeID).roomList;
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(
            @PathParam("homeId") Long homeId,
            @PathParam("roomId") Long roomId) {

        Home h = Home.findHomeById(homeId);
        Room r = Room.findRoomById(roomId);

        if (h.roomList.contains(r)) {
            return r;
        } else {
            throw new WebApplicationException("Room with the id: " + r.id + " does not exist in this home.", 404);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Room addRoom(
            @PathParam("homeId") Long homeId,
            @Valid Room room) {

        Home h = Home.findHomeById(homeId);
        h.addRoom(room);
        return room;
    }

    @PUT
    @Path("/{roomId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Room updateRoom(
            @PathParam("homeId") Long homeId,
            @PathParam("roomId") Long roomId,
            @Valid Room room) {

        Home h = Home.findHomeById(homeId);
        Room r = Room.findRoomById(roomId);

        if (h.roomList.contains(r)) {
            r.name = room.name;
            r.description = room.description;
            return r;
        } else {
            throw new WebApplicationException("Room with id: " + r.id + " does not exist in this room", 404);
        }
    }

    @DELETE
    @Path("/{roomId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public boolean deleteRoom(
            @PathParam("homeId") Long homeId,
            @PathParam("roomId") Long roomId) {

        Home h = Home.findHomeById(homeId);
        Room r = Room.findRoomById(roomId);

        if (h.roomList.contains(r)) {
            return h.roomList.remove(r);
        } else {
            throw new WebApplicationException("Room with id: " + r.id + " does not exist in this room", 404);
        }
    }

}
