package ch.hftm.api.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ch.hftm.api.logic.TimedEvent;
import ch.hftm.api.models.Task;

@Path("/timedevent")
public class TimedEventResource {

    @Inject
    TimedEvent timedEvent;

    @GET
    public List<Task> listAll() {
        try {
            timedEvent.createTimedEvent(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Task.listAll();
    }
}
