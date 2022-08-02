package ch.hftm.api.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import ch.hftm.api.logic.WeatherService;
import ch.hftm.api.models.Window;
import ch.hftm.api.models.enums.State;
import ch.hftm.api.models.enums.StateType;

@Path("/windows/{id}/state")
public class StateResource {

    @Inject
    WeatherService weatherService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getWindowState(
            @PathParam("id") Long windowId,
            @QueryParam("state") @Valid State state) {

        Window w = Window.findWindowById(windowId);
        weatherService.updateWindowStatus(w);

        if (state.equals(State.CURRENT)) {
            return "Current state: " + w.currentState;
        } else if (state.equals(State.DESIRED)) {
            return "Desired state: " + w.desiredState;
        } else {
            throw new WebApplicationException("State with the name " + state.toString() + " does not exist.", 404);
        }
    }

    @PATCH
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String setWindowState(
            @PathParam("id") Long windowId,
            @QueryParam("state") @Valid State state,
            @QueryParam("val") @Valid StateType value) {

        Window w = Window.findWindowById(windowId);

        if (state.equals(State.CURRENT)) {
            w.currentState = value;
            return "Set current state to: " + w.currentState;
        } else if (state.equals(State.DESIRED)) {
            w.desiredState = value;
            return "Set desired state to: " + w.desiredState;
        } else {
            throw new WebApplicationException("Sorry, we messed up :(", 500);
        }

    }
}
