package org.palax.command.tenant;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.BidService;
import org.palax.service.UserPrincipalService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetTenantBidCommand} class implements {@link Command} used for get tenant bid
 *
 * @author Taras Palashynskyy
 */

public class GetTenantBidCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.tenant-bid"))
            return PathManager.getProperty("path.page.error-perm");

        request.setAttribute("bidList", BidService.getAllBidByUserID(user));

        page = PathManager.getProperty("path.page.tenant-bid");

        return page;
    }
}
