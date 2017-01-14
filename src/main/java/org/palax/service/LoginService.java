package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.User;

/**
 * The {@code LoginService} service is a specified login API for working with the {@link org.palax.dao.UserDao}
 *
 * @author Taras Palashynskyy
 */

public class LoginService {

    private LoginService() {

    }

    /**
     * Method checks whether there is a {@link User} which find by {@code login}
     * and the associated {@code passwd}
     *
     * @param login {@link User} login
     * @param passwd {@link User} password
     * @return returns the {@link User} who has been found or {@code null}
     */
    public static User login(String login, String passwd) {

        User user = MySQLDAOFactory.getUserDao().getUserByLogin(login);

        if(user != null && user.getPassword().equals(passwd)) {
            user.setRole(MySQLDAOFactory.getRoleDao().getRoleById(user.getRole().getRoleId()));
            return user;
        }

        return null;
    }
}
