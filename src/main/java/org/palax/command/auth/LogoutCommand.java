package org.palax.command.auth;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code LogoutCommand} class implements {@link Command}
 * used for remove {@code user} from session
 *
 * @author Taras Palashynskyy
 */

public class LogoutCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        String login = ((User)request.getSession().getAttribute("user")).getLogin();
        request.getSession().removeAttribute("user");
        request.setAttribute("login", login);

        page = PathManager.getProperty("path.page.login");

        return page;
    }
}
