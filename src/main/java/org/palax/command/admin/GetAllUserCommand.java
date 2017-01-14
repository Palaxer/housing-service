package org.palax.command.admin;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetAllUserCommand} class implements {@link Command}
 * used for get all user
 *
 * @author Taras Palashynskyy
 */

public class GetAllUserCommand implements Command{

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.user"))
            return PathManager.getProperty("path.page.error-perm");

        request.setAttribute("userList", UserService.getAllUser());

        page = PathManager.getProperty("path.page.user");

        return page;
    }
}
