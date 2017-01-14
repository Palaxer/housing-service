package org.palax.command.advisor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Bid;
import org.palax.entity.Brigade;
import org.palax.entity.User;
import org.palax.entity.WorkPlane;
import org.palax.service.BidService;
import org.palax.service.BrigadeService;
import org.palax.service.UserPrincipalService;
import org.palax.service.WorkPlaneService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code SetBrigadeCommand} class implements {@link Command}
 * used for set brigade to work plane
 *
 * @author Taras Palashynskyy
 */

public class SetBrigadeCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(SetBrigadeCommand.class);

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
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("leadTime"), df);

            WorkPlane workPlane = new WorkPlane();
            workPlane.setUserAdvisor(user);
            workPlane.setStatus("NEW");
            workPlane.setBid(bid);
            workPlane.setWorkTime(Timestamp.valueOf(dateTime));

            String brigadeId = request.getParameter("brigadeId");
            Brigade brigade = null;
            if(brigadeId != null)
                brigade = BrigadeService.getBrigadeById(Long.parseLong(brigadeId));


            if(WorkPlaneService.setBrigadeToWorkPlane(brigade, workPlane)) {
                bid.setStatus("IN WORK");
                MySQLDAOFactory.getBidDao().updateBid(bid);
                page = PathManager.getProperty("path.redirect.advisor");
            } else {
                request.setAttribute("bid", bid);
                request.setAttribute("time", df.format(workPlane.getWorkTime().toLocalDateTime()));
                request.setAttribute("brigadeList",
                        BrigadeService.getMatchBrigade(bid, workPlane.getWorkTime().toLocalDateTime(), 20));

                page = PathManager.getProperty("path.page.bid-info");
            }

        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }

        return page;
    }
}
