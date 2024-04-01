package org.acme.forSimCard;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.forSimCard.SimCard;

@ApplicationScoped
public class SimCardRepository implements PanacheRepository<SimCard> {
}
