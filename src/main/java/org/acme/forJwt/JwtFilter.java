package org.acme.forJwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;


import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.logging.Logger;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        if (path.equals("/users") || path.equals("/auth/login")) {
            return; // Skip authentication for these endpoints
        }

        if (path.startsWith("/citizen")) {

            String authorizationHeader = requestContext.getHeaderString("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Claims claims = Jwts.parser()
                            .setSigningKey(JwtConstants.SECRET_KEY)
                            .parseClaimsJws(token)
                            .getBody();

                    // Extract roles from the claims and verify if the user has access to the endpoint
                    List<String> roles = claims.get("roles", List.class);
                    if (!verifyAccess(roles, path)) {
                        throw new Exception("Access Denied");
                    }

                } catch (Exception e) {
                    logger.severe("JWT validation failed: " + e.getMessage());
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid token or access denied: " + e.getMessage())
                            .build());
                }
            } else {
                logger.warning("Missing or invalid Authorization header");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Missing or invalid Authorization header")
                        .build());
            }
        }
    }

    private boolean verifyAccess(List<String> roles, String path) {
        // Your logic to verify if the roles include the necessary role to access the path
        // For simplicity, assuming "ADMIN" role can access all paths and "USER" role can access only GET methods
        if (roles.contains("ADMIN")) {
            return true;
        } else if (roles.contains("USER") && (path.startsWith("/citizen") && !path.contains("/citizen/"))) {
            // Restricting "USER" role to only allow accessing listing all citizens (GET method without id in the path)
            return true;
        }
        return false;
    }
}
