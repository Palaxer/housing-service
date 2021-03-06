package org.palax.command.admin;

import org.palax.command.Command;
import org.palax.entity.Brigade;
import org.palax.entity.User;
import org.palax.service.BrigadeService;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code  AddBrigadeCommand} class implements {@link Command}
 * used for add brigade
 *
 * @author Taras Palashynskyy
 */

public class AddBrigadeCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.brigade"))
            return PathManager.getProperty("path.page.error-perm");

        Brigade brigade = new Brigade();
        brigade.setWorkType(WorkTypeService.getWorkTypeByName(request.getParameter("workType")));
        brigade.setBrigadeName(request.getParameter("brigadeName"));

        if(!BrigadeService.addBrigade(brigade)) {
            request.setAttribute("invalidData", true);
            request.setAttribute("brigade", brigade);
        } else
            request.setAttribute("successUpdate", true);

        request.setAttribute("brigadeList", BrigadeService.getAllBrigade());

        request.setAttribute(   "workType", WorkTypeService.getAllWorkType());

        page = PathManager.getProperty("path.page.brigade");

        return page;
    }
}
