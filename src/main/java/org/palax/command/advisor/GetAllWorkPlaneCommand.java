package org.palax.command.advisor;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkPlaneService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetAllWorkPlaneCommand} class implements {@link Command}
 * used for get all work plane
 *
 * @author Taras Palashynskyy
 */

public class GetAllWorkPlaneCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.advisor-work-plane"))
            return PathManager.getProperty("path.page.error-perm");

        request.setAttribute("workPlaneList", WorkPlaneService.getAllWorkPlane());

        page = PathManager.getProperty("path.page.advisor-work-plane");

        return page;
    }
}
