package ch.hftm.api.resources;

import java.util.List;

import javax.persistence.EntityManager;
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

import ch.hftm.api.models.Home;

@Path("/homes")
public class HomeResource {

    EntityManager manager;

    /**
     * Returns all Homes
     * 
     * @return List<Home>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Home> getHomes() {
        return Home.findAllHomes();
    }

    /**
     * Returns home with the id defined in the parameter
     * 
     * @param id
     * @return Home
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Home getHome(@PathParam("id") Long id) {
        return Home.findHomeById(id);
    }

    /**
     * Creates a new home
     * 
     * @param home
     * @return Home
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Home addHome(Home home) {
        home.persist();
        return home;
    }

    /**
     * Updates a home's information
     * 
     * @param id
     * @param home
     * @return Home
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Home updateHome(@PathParam("id") Long id, Home home) {
        Home entity = Home.findHomeById(id);
        if (entity == null) {
            throw new WebApplicationException("Entry with id of " + id + " does not exist.", 404);
        }
        entity.description = home.description;
        entity.name = home.name;
        entity.postalCode = home.postalCode;

        return entity;
    }

    /**
     * Deletes the home with the defined id in the parameter
     * 
     * @param id
     * @return boolean
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public boolean deleteHome(@PathParam("id") Long id) {
        return Home.deleteHomeById(id);
    }
}
