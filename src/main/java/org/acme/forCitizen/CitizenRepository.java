package org.acme.forCitizen;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.forCitizen.Citizen;

@ApplicationScoped
public class CitizenRepository implements PanacheRepository<Citizen> {
}
