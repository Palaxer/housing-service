package org.palax.command.crew;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkPlaneService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CompleteWorkCommand} class implements {@link Command}
 * used for complete bid in work plane
 *
 * @author Taras Palashynskyy
 */

public class CompleteWorkCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.crew-work-plane"))
            return PathManager.getProperty("path.page.error-perm");

        Long index = Long.parseLong(request.getParameter("index"));

        WorkPlaneService.completeWorkPlaneById(index);

        page = PathManager.getProperty("path.page.crew-work-plane");

        return page;
    }
}
