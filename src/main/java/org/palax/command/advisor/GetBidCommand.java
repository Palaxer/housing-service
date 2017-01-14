package org.palax.command.advisor;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.strategy.*;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetBidCommand} class implements {@link Command}
 * used for get all bid
 *
 * @author Taras Palashynskyy
 */

public class GetBidCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.advisor-bid"))
            return PathManager.getProperty("path.page.error-perm");

        GetBid bidStrategy;

        switch (request.getParameter("status").toUpperCase()) {
            case "ALL" :
                request.setAttribute("status", "all");
                bidStrategy = new GetBid(new GetAllBid());
                break;
            case "NEW" :
                request.setAttribute("status", "new");
                bidStrategy = new GetBid(new GetNewBid());
                break;
            case "IN WORK" :
                request.setAttribute("status", "in work");
                bidStrategy = new GetBid(new GetInWorkBid());
                break;
            case "COMPLETE" :
                request.setAttribute("status", "complete");
                bidStrategy = new GetBid(new GetCompleteBid());
                break;
            case "CLOSE" :
                request.setAttribute("status", "close");
                bidStrategy = new GetBid(new GetCloseBid());
                break;
            default:
                request.setAttribute("status", "all");
                bidStrategy = new GetBid(new GetAllBid());
                break;
        }

        request.setAttribute("bidList", bidStrategy.getBid());

        page = PathManager.getProperty("path.page.advisor-bid");

        return page;
    }
}
