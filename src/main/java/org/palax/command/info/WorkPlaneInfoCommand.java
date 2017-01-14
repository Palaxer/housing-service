package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.entity.WorkPlane;
import org.palax.service.*;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code WorkPlaneInfoCommand} class implements {@link Command}
 * used for get information about work plane
 *
 * @author Taras Palashynskyy
 */

public class WorkPlaneInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(WorkPlaneInfoCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.work-plane-info"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            WorkPlane workPlane = WorkPlaneService.getWorkPlaneById(Long.parseLong(request.getParameter("index")));

            request.setAttribute("workPlane", workPlane);
        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        page = PathManager.getProperty("path.page.work-plane-info");

        return page;
    }
}
