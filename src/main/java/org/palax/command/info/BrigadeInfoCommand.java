package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Brigade;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The {@code  BrigadeInfoCommand} class implements {@link Command}
 * used for get information about brigade
 *
 * @author Taras Palashynskyy
 */

public class BrigadeInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(BrigadeInfoCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.brigade-info"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            Brigade brigade = user.getBrigade();

            if(brigade.getBrigadeId() == null) {
                Long id = Long.parseLong(request.getParameter("index"));
                brigade = MySQLDAOFactory.getBrigadeDao().getBrigadeById(id);
            }

            List<User> userList = UserService.getUserByBrigadeId(brigade.getBrigadeId());

            request.setAttribute("brigade", brigade);
            request.setAttribute("userList", userList);

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }
        page = PathManager.getProperty("path.page.brigade-info");

        return page;
    }
}
