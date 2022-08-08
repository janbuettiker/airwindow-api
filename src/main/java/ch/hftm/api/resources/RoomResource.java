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

    /**
     * Lists all rooms of the house defined in the path parameter
     * 
     * @param homeID
     * @return List<Room>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms(@PathParam("homeId") Long homeID) {
        return Room.find("home_id = ?1", homeID).list();
    }

    /**
     * Returns the room with the defined room id
     * Only returns the room, if the room is part of the defined home id
     * 
     * @param homeid
     * @param roomid
     * @return Room
     */
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

    /**
     * Create a new room within the given home
     * 
     * @param homeid
     * @param room
     * @return Room
     */
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

    /**
     * Update room information
     * 
     * @param homeid
     * @param roomid
     * @return Room
     */
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

    /**
     * Removes the room with the given id.
     * Only removes the room, if it is part of the given home id
     * 
     * @param roomid
     * @param homeid
     * @return boolean
     */
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
