package org.acme.forUser;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @Inject
    UserRespository userRespository;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        userRespository.persist(user);

        return Response.status(Response.Status.CREATED).entity(user).build();
    }



    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        User user = userRespository.findById(id);
        if (user != null) {
            userRespository.delete(user);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

