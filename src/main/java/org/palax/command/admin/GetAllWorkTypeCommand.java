package org.palax.command.admin;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetAllWorkTypeCommand} class implements {@link Command}
 * used for get all work type
 *
 * @author Taras Palashynskyy
 */

public class GetAllWorkTypeCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.work-type"))
            return PathManager.getProperty("path.page.error-perm");

        request.setAttribute("workTypeList", WorkTypeService.getAllWorkType());

        page = PathManager.getProperty("path.page.work-type");

        return page;
    }
}
