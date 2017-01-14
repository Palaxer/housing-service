package org.palax.command.tenant;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Bid;
import org.palax.entity.User;
import org.palax.service.BidService;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code AddTenantBidCommand} class implements {@link Command} used for add tenant bid
 *
 * @author Taras Palashynskyy
 */

public class AddTenantBidCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(AddTenantBidCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page =  PathManager.getProperty("path.page.add-bid");

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.add-bid"))
            return PathManager.getProperty("path.page.error-perm");

        request.setAttribute("workType", WorkTypeService.getAllWorkType());

        Bid bid = new Bid();
        bid.setStatus("NEW");
        bid.setUserTenant(user);
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setWorkType(WorkTypeService.getWorkTypeByName(request.getParameter("workType")));
        bid.setDescription(request.getParameter("desc").trim());
        try {
            bid.setWorkScope(Long.parseLong(request.getParameter("workScope")));
        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("leadTime"), df);

        bid.setLeadTime(Timestamp.valueOf(dateTime));

        boolean flag = false;

        if (!BidService.workScopeValid(bid.getWorkScope())) {
            request.setAttribute("invalidWorkScope", "invalid");
            flag = true;
        }

        if (!BidService.leadTimeValid(bid.getLeadTime(), 3L)) {
            request.setAttribute("invalidLeadTime", "invalid");
            flag = true;
        }

        if (flag) {
            request.setAttribute("invalidData", true);
            request.setAttribute("bid", bid);
            request.setAttribute("time", dateTime.format(df));
        } else if (BidService.addBid(bid))
            page = PathManager.getProperty("path.redirect.tenant");
        else
            request.setAttribute("bidExist", true);

        return page;
    }
}
