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

/**
 * This is the resource that will be used by the window controller.
 * The controller will check the desired window state with the GET function.
 * It then compares the desired state to the current state and if necessary,
 * opens or closes the window.
 * 
 * Afterwards, the controller will set the current state of the window.
 */
@Path("/windows/{id}/state")
public class StateResource {

    @Inject
    WeatherService weatherService;

    /**
     * Gets the state of a given window
     * 
     * @param windowId
     * @param state    - Desired or current state
     * @return String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getWindowState(
            @PathParam("id") Long windowId,
            @QueryParam("state") @Valid State state) {

        Window w = Window.findWindowById(windowId);
        // Check the weather API if it is raining, if so, we set the desired state to
        // closed through this service bean.
        weatherService.updateWindowStatus(w);

        if (state.equals(State.CURRENT)) {
            return "Current state: " + w.currentState;
        } else if (state.equals(State.DESIRED)) {
            return "Desired state: " + w.desiredState;
        } else {
            throw new WebApplicationException("State with the name " + state.toString() + " does not exist.", 404);
        }
    }

    /**
     * Sets the state of a given window
     * 
     * @param windowId
     * @param state    - Set desired or current state
     * @param value    - To the desired value of open or closed
     * @return String
     */
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
