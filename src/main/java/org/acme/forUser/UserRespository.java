package org.acme.forUser;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.forUser.User;

@ApplicationScoped
public class UserRespository implements PanacheRepository<User> {
    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
