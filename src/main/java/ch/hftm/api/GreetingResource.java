package ch.hftm.api;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import ch.hftm.api.models.Home;
import ch.hftm.api.models.Room;
import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.StateType;

@Path("/hello")
public class GreetingResource {

    Integer counter = 0;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Home hello() {

        if (counter == 0) {
            Home h1 = new Home();
            h1.name = "HFTM Grenchen";
            h1.description = "Schulgebäude in Grenchen";

            Room r1 = new Room();
            r1.name = "Raum 325";
            r1.description = "Schulraum PoE";
            Room r2 = new Room();
            r2.name = "Labor";
            r2.description = "Science, Bitch";

            Window w1 = new Window();
            w1.name = "Fenster süd";
            w1.currentState = StateType.OPEN;
            w1.desiredState = StateType.OPEN;
            w1.description = "Frosch sagt hallo";

            Window w2 = new Window();
            w2.name = "Dachfenster Nord";
            w2.currentState = StateType.ANGLED;
            w2.desiredState = StateType.OPEN;
            w2.description = "Cooles Fenster";

            Window w3 = new Window();
            w3.name = "Fenster Türe";
            w3.currentState = StateType.CLOSED;
            w3.desiredState = StateType.OPEN;
            w3.description = "Über der Türe";

            h1.addRoom(r1);
            h1.addRoom(r2);
            r1.addWindow(w1);
            r2.addWindow(w2);
            r2.addWindow(w3);

            h1.persist();
            this.counter++;
            return h1;

        } else {
            throw new WebApplicationException("Demo data has already been created", 404);
        }

    }
}