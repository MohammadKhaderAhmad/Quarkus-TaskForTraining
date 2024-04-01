package org.acme.forQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/JoinQuerys")
public class JoinQueryResource {

    @Inject
    JoinQuerys joinQueries;


    @GET
    @Path("/hqlToShowTheCitizenWithFalseActiveCard")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JoinQueryResult> getResultsFromHQL() {
        return joinQueries.executeHQL();
    }
}
