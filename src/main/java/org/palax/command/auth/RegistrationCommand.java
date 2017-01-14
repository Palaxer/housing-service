package org.palax.command.auth;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code RegistrationCommand} class implements {@link Command}
 * used for registration new user
 *
 * @author Taras Palashynskyy
 */

public class RegistrationCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.registration");

        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("passwd"));
        user.setFirstName(request.getParameter("first-name"));
        user.setLastName(request.getParameter("last-name"));
        user.setStreet(request.getParameter("street"));
        user.setHouseNumber(request.getParameter("house-number"));
        user.setCity(request.getParameter("city"));
        try {
            user.setApartment(Long.parseLong(request.getParameter("apartment")));

            InvalidData.Builder builder = InvalidData.newBuilder("invalid");
            boolean flag = false;

            if (!UserService.loginValid(user.getLogin())) {
                builder.setInvalidLoginAttr();
                flag = true;
            }
            if (!UserService.firstNameValid(user.getFirstName())) {
                builder.setInvalidFirstNameAttr();
                flag = true;
            }
            if (!UserService.lastNameValid(user.getLastName())) {
                builder.setInvalidLastNameAttr();
                flag = true;
            }
            if (!UserService.streetValid(user.getStreet())) {
                builder.setInvalidStreetAttr();
                flag = true;
            }
            if (!UserService.houseNumberValid(user.getHouseNumber())) {
                builder.setInvalidHouseAttr();
                flag = true;
            }
            if (!UserService.apartmentValid(user.getApartment())) {
                builder.setInvalidApartment();
                flag = true;
            }
            if (!UserService.cityValid(user.getCity())) {
                builder.setInvalidCityAttr();
                flag = true;
            }
            if (flag)
                request.setAttribute("invalidData", builder.build());

            else if (UserService.createUser(user, "TENANT")) {
                page = PathManager.getProperty("path.page.login");
                request.setAttribute("login", user.getLogin());
            } else
                request.setAttribute("loginExist", true);

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        request.setAttribute("user", user);

        return page;
    }
}
