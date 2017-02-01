package org.palax.command.admin;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The {@code GetAllUserCommand} class implements {@link Command}
 * used for get all user
 *
 * @author Taras Palashynskyy
 */

public class GetAllUserCommand implements Command{

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        int elementPerPage = 2;
        int currentPage = 1;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.user"))
            return PathManager.getProperty("path.page.error-perm");

        Long count = UserService.getAllUserCount();

        int pageNumber = (int) Math.ceil(count * 1.0 / elementPerPage);
        if(request.getParameter("page") != null)
            currentPage = Integer.parseInt(request.getParameter("page"));

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("userList", UserService.getAllUser(currentPage * elementPerPage - elementPerPage,
                elementPerPage));

        page = PathManager.getProperty("path.page.user");

        return page;
    }
}
