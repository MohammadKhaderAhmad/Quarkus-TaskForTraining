package org.acme.forQuery;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.forQuery.NativeAndHibernateQuery;
import org.acme.forSimCard.SimCard;

import java.util.List;

@Path("/NativeAndHibernateQueryTest")
public class NativeAndHibernateQueryTest {
    @Inject
    NativeAndHibernateQuery nativeAndHibernateQuery;

    @GET
    @Path("/NativeQueryToGetFalseActiveSimCard")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SimCard> getInactiveSimCardsWithNativeQuery() {
        return nativeAndHibernateQuery.getInactiveSimCardsWithNativeQuery();
    }

    @GET
    @Path("/HibernateQueryToGetTrueActiveSimCard")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SimCard> getInactiveSimCardsWithHQL() {
        return nativeAndHibernateQuery.getInactiveSimCardsWithHQL();
    }
}
