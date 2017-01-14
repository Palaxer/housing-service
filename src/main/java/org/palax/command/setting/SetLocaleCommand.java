package org.palax.command.setting;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code SetLocaleCommand} class implements {@link Command} used for change local
 *
 * @author Taras Palashynskyy
 */

public class SetLocaleCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.profile");

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.profile"))
            return PathManager.getProperty("path.page.error-perm");

        switch (request.getParameter("language")) {
            case "ru" :
                session.setAttribute("language", "ru");
                break;
            case "en" :
                session.setAttribute("language", "en");
                break;
            default:
                session.setAttribute("language", "en");
                break;
        }

        return page;
    }
}
