package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.BrigadeService;
import org.palax.service.RoleService;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UserInfoCommand} class implements {@link Command}
 * used for get information about user
 *
 * @author Taras Palashynskyy
 */

public class UserInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(UserInfoCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.user-info");

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.user-info"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            String login = request.getParameter("login");

            if(login != null && !login.isEmpty()) {
                page = userFindVerification(request, UserService.getUserByLogin(login));
            } else {
                Long id = Long.parseLong(request.getParameter("index"));
                page = userFindVerification(request, UserService.getUserById(id));
            }
            request.setAttribute("roleList", RoleService.getAllRole());
            request.setAttribute("brigadeList", BrigadeService.getAllBrigade());

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        return page;
    }

    private String userFindVerification(HttpServletRequest request,User userInfo) {
        if (userInfo != null)
            request.setAttribute("user", userInfo);
        else {
            request.setAttribute("notFound", true);
            request.setAttribute("userList", UserService.getAllUser());

            return PathManager.getProperty("path.page.user");
        }

        return PathManager.getProperty("path.page.user-info");
    }
}
