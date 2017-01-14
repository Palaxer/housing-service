package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.entity.WorkType;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UpdateWorkTypeCommand} class implements {@link Command}
 * used for update information about work type
 *
 * @author Taras Palashynskyy
 */

public class UpdateWorkTypeCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(UpdateWorkTypeCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.work-type"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            WorkType workType = new WorkType();
            workType.setTypeName(request.getParameter("typeName"));

            workType.setWorkTypeId(Long.parseLong(request.getParameter("index")));


            if(!WorkTypeService.updateWorkType(workType)) {
                request.setAttribute("invalidData", true);
            } else
                request.setAttribute("successUpdate", true);

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        page = PathManager.getProperty("path.redirect.work-type");

        return page;
    }
}
