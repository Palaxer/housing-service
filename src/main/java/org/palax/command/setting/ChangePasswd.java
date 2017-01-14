package org.palax.command.setting;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code ChangePasswd} class implements {@link Command} used for change user password
 *
 * @author Taras Palashynskyy
 */

public class ChangePasswd implements Command {
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.profile");

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.profile"))
            return PathManager.getProperty("path.page.error-perm");

        if(request.getParameter("passwd").equals(request.getParameter("passwd1"))) {
            user.setPassword(request.getParameter("passwd"));
            if(UserService.updateUser(user))
                request.setAttribute("successUpdatePasswd", true);
            else
                request.setAttribute("invalidUpdatePasswd", true);
        } else
            request.setAttribute("invalidDataPasswd", true);

        return page;
    }
}
