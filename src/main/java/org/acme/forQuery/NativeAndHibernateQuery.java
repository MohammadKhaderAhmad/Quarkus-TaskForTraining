package org.acme.forQuery;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.acme.forSimCard.SimCard;

import java.util.List;

@ApplicationScoped
public class NativeAndHibernateQuery {

    @Inject
    EntityManager entityManager;

    // Native SQL Query for retrieving all SimCards where isActive is false
    public List<SimCard> getInactiveSimCardsWithNativeQuery() {
        String nativeSql = "SELECT * FROM SimCard WHERE isActive = false";
        Query nativeQuery = entityManager.createNativeQuery(nativeSql, SimCard.class);
        return nativeQuery.getResultList();
    }

    // Hibernate Query (HQL) for retrieving all SimCards where isActive is true
    public List<SimCard> getInactiveSimCardsWithHQL() {
        String hql = "FROM SimCard s WHERE s.isActive = true";
        Query hqlQuery = entityManager.createQuery(hql, SimCard.class);
        return hqlQuery.getResultList();
    }

}
