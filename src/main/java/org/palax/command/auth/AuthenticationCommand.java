package org.palax.command.auth;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code AuthenticationCommand} class implements {@link Command}
 * used for auto redirect user to base page
 *
 * @author Taras Palashynskyy
 */

public class AuthenticationCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.login");

        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");

            page = UserPrincipalService.userBaseMapping(user);
        }

        return page;
    }
}
