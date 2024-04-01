package org.acme.forUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.forUser.User;
import org.acme.forUser.UserRespository;

@ApplicationScoped
public class UserService {
    @Inject
    UserRespository userRespository;

    public boolean validateUser(String username, String password) {
        User user = userRespository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}

