package org.acme.forJwt;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.forJwt.JwtUtil;
import org.acme.forUser.User;
import org.acme.forUser.UserRespository;
import org.acme.forUser.UserService;

import java.util.List;

@Path("/auth")
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    UserRespository userRespository;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        if (userService.validateUser(username, password)) {
            // Get user roles from the database (assuming User entity contains roles field)
            User user = userRespository.findByUsername(username);
            List<String> roles = user.getRoles(); // Assuming getRoles method exists in User entity
            String token = JwtUtil.generateToken(username, roles);
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }

}

