package org.palax.command.tenant;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.BidService;
import org.palax.service.UserPrincipalService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CloseBidCommand} class implements {@link Command} used for close bid
 *
 * @author Taras Palashynskyy
 */

public class CloseBidCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(CloseBidCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.tenant-bid"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            BidService.changeStatusBid(Long.parseLong(request.getParameter("index")), "CLOSE");

            page = PathManager.getProperty("path.redirect." + user.getRole().getRoleType().toLowerCase());

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        return page;
    }
}
