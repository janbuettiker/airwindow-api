package ch.hftm.api;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.hftm.api.models.Home;
import ch.hftm.api.models.Room;
import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Home hello() {

        Home home1 = new Home();
        home1.name = "Bruchbude";
        home1.description = "keine";

        Room room1 = new Room();
        room1.name = "Badzimmer";

        Window window1 = new Window();
        window1.name = "Fenster süd";
        window1.currentState = StateType.OPEN;

        home1.addRoom(room1);
        room1.addWindow(window1);

        home1.persist();

        return home1;

    }
}