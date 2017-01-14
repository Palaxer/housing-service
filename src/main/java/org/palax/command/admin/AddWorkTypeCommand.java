package org.palax.command.admin;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.entity.WorkType;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code AddWorkTypeCommand} class implements {@link Command}
 * used for add new work type
 *
 * @author Taras Palashynskyy
 */

public class AddWorkTypeCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.work-type"))
            return PathManager.getProperty("path.page.error-perm");

        WorkType workType = new WorkType();
        workType.setTypeName(request.getParameter("typeName"));

        if(!WorkTypeService.addWorkType(workType)) {
            request.setAttribute("invalidData", true);
            request.setAttribute("typeName", workType.getTypeName());
        } else
            request.setAttribute("successUpdate", true);

        request.setAttribute("workTypeList", WorkTypeService.getAllWorkType());

        page = PathManager.getProperty("path.page.work-type");

        return page;
    }
}
