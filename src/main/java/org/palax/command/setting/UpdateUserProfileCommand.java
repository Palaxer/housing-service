package org.palax.command.setting;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UpdateUserProfileCommand} class implements {@link Command} used for update user
 *
 * @author Taras Palashynskyy
 */

public class UpdateUserProfileCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UpdateUserProfileCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.profile");

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.profile"))
            return PathManager.getProperty("path.page.error-perm");

        User updateUser = new User();
        updateUser.setFirstName(request.getParameter("first-name"));
        updateUser.setLastName(request.getParameter("last-name"));
        updateUser.setStreet(request.getParameter("street"));
        updateUser.setHouseNumber(request.getParameter("house-number"));
        updateUser.setCity(request.getParameter("city"));
        try {
            updateUser.setApartment(Long.parseLong(request.getParameter("apartment")));
        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        boolean flag = false;

        if (!UserService.firstNameValid(updateUser.getFirstName())) {
            request.setAttribute("invalidFirstName", "invalid");
            flag = true;
        }
        if (!UserService.lastNameValid(updateUser.getLastName())) {
            request.setAttribute("invalidLastName", "invalid");
            flag = true;
        }
        if (!UserService.streetValid(updateUser.getStreet())) {
            request.setAttribute("invalidStreet", "invalid");
            flag = true;
        }
        if (!UserService.houseNumberValid(updateUser.getHouseNumber())) {
            request.setAttribute("invalidHouse", "invalid");
            flag = true;
        }
        if (!UserService.apartmentValid(updateUser.getApartment())) {
            request.setAttribute("invalidApartment", "invalid");
            flag = true;
        }
        if (!UserService.cityValid(updateUser.getCity())) {
            request.setAttribute("invalidCity", "invalid");
            flag = true;
        }

        if (flag)
            request.setAttribute("invalidData", true);
        else if (UserService.updateUser(updateUser)) {
            request.setAttribute("successUpdate", true);
            UserService.userDuplicate(updateUser, user);
        } else
            request.setAttribute("invalidUpdate", true);

        return page;
    }
}
