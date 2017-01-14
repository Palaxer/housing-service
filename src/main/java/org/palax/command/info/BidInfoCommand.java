package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Bid;
import org.palax.entity.User;
import org.palax.service.BidService;
import org.palax.service.BrigadeService;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkPlaneService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

/**
 * The {@code BidInfoCommand} class implements {@link Command}
 * used for get information about bid
 *
 * @author Taras Palashynskyy
 */

public class BidInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(BidInfoCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.bid-info"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            Bid bid = BidService.getBidByID(Long.parseLong(request.getParameter("index")));

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            request.setAttribute("bid", bid);
            request.setAttribute("time", df.format(bid.getLeadTime().toLocalDateTime()));
            request.setAttribute("brigadeList",
                    BrigadeService.getMatchBrigade(bid, bid.getLeadTime().toLocalDateTime(), 20));

            if(!(bid.getStatus().equals("NEW") || bid.getStatus().equals("CLOSE")))
                request.setAttribute("workPlane", WorkPlaneService.getWorkPlaneByBidId(bid.getBidId()));

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        page = PathManager.getProperty("path.page.bid-info");

        return page;
    }
}
