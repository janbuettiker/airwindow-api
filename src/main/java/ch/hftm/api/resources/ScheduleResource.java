package ch.hftm.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import ch.hftm.api.logic.ScheduleService;
import ch.hftm.api.models.Task;
import ch.hftm.api.models.enums.StateType;

@Path("/windows/{id}/tasks")
public class ScheduleResource {

    private List<Task> allTasks;
    private List<Task> windowTasks;

    @Inject
    ScheduleService scheduleService;

    /**
     * Gets all task associated to the given window.
     * Tasks are only listed after they have been triggered!
     * 
     * @param windowId
     * @return List<Task>
     */
    @GET
    public List<Task> listAll(@PathParam("id") Long windowId) {

        windowTasks = new ArrayList<>();
        allTasks = Task.listAll();

        for (Task t : allTasks) {
            if (t.windowId.equals(windowId)) {
                windowTasks.add(t);
            }
        }
        return windowTasks;
    }

    /**
     * Creates a one time task. This task will run in the given amount of minutes in
     * the future from when the task is run.
     * 
     * @param windowId
     * @param inMin    - In how many minutes should the task run
     * @param st       - Open or close the window
     * @return Boolean
     */
    @POST
    @Path("/ot")
    public Boolean createOneTimeTask(@PathParam("id") Long windowId, @QueryParam("inMin") Integer inMin,
            @QueryParam("state") StateType st) {

        try {
            scheduleService.createTimedEvent(inMin, windowId, st);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Failed to schedule one-time task", 500);
        }
    }

    /**
     * Creates a scheduled task that will run every day at the given time.
     * The time is configured through hour and minute..
     * 
     * @param windowId
     * @param hour     - Hour between 0 - 23
     * @param minute   - Minute between 0 - 59
     * @param st       - Open or close the window
     * @return Boolean
     */
    @POST
    @Path("/st")
    public Boolean createScheduledTask(@PathParam("id") Long windowId, @QueryParam("h") Integer hour,
            @QueryParam("m") Integer minute, @QueryParam("state") StateType st) {

        try {

            scheduleService.createDailyScheduledEvent(hour, minute, windowId, st);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException("Failed to schedule daily task", 500);
        }
    }
}
