package org.acme.forQuery;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.forQuery.JoinQueryResult;

import java.util.List;

@ApplicationScoped
public class JoinQuerys {

    @PersistenceContext
    private EntityManager entityManager;

   /* public List<JoinQueryResult> executeNativeSQL() {
        String sql = "SELECT c.id as citizenId, c.name as citizenName, c.gender, s.id as simCardId FROM Citizen c JOIN SimCard s ON c.sim_card_id = s.id WHERE s.isActive = true";
        return entityManager.createNativeQuery(sql, "JoinQueryResultMapping").getResultList();
    }*/

    public List<JoinQueryResult> executeHQL() {
        String hql = "SELECT new org.acme.forQuery.JoinQueryResult(c.id, c.name, c.gender, s.id) FROM Citizen c JOIN c.simCard s WHERE s.isActive = false";
        return entityManager.createQuery(hql, JoinQueryResult.class).getResultList();
    }
}
