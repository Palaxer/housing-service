package org.palax.command.crew;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.entity.WorkPlane;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkPlaneService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The {@code GetWorkPlaneCommand} class implements {@link Command}
 * used for get current crew work plane
 *
 * @author Taras Palashynskyy
 */

public class GetWorkPlaneCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.crew-work-plane"))
            return PathManager.getProperty("path.page.error-perm");

        List<WorkPlane> workPlaneList = WorkPlaneService.getAllWorkPlaneByBrigadeId(user.getBrigade().getBrigadeId());

        request.setAttribute("workPlaneList", workPlaneList);

        page = PathManager.getProperty("path.page.crew-work-plane");

        return page;
    }
}
