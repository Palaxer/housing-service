package org.palax.command.auth;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.LoginService;
import org.palax.service.UserPrincipalService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code LoginCommand} class implements {@link Command}
 * used for user logon
 *
 * @author Taras Palashynskyy
 */

public class LoginCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = null;

        String login = request.getParameter("login");
        String passwd = request.getParameter("passwd");

        if (login != null && passwd != null) {
            user = LoginService.login(login, passwd);
            if (user != null) {
                request.getSession().setAttribute("user", user);
                page = UserPrincipalService.userBaseMapping(user);

                return page;
            }
        }

        request.setAttribute("loginError", true);
        request.setAttribute("login", login);
        page = PathManager.getProperty("path.page.login");

        return page;
    }
}
