package org.palax.command.util;

import org.palax.command.Command;
import org.palax.service.WorkTypeService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code RedirectCommand} class implements {@link Command} used for redirection between page
 * to redirect need parameter {@code "to"}
 *
 * @author Taras Palashynskyy
 */

public class RedirectCommand implements Command {

    /**Map which store page redirection mapping. */
    private Map<String, String> redirectMap;

    /**
     * Constructor initializes {@code redirectMap}
     */
    public RedirectCommand() {
        redirectMap = new HashMap<>();
        redirectMap.put("LOGIN", PathManager.getProperty("path.page.login"));
        redirectMap.put("REGISTRATION", PathManager.getProperty("path.page.registration"));
        redirectMap.put("TENANTBID", PathManager.getProperty("path.page.tenant-bid"));
        redirectMap.put("ADDBID", PathManager.getProperty("path.page.add-bid"));
        redirectMap.put("PROFILE", PathManager.getProperty("path.page.profile"));
        redirectMap.put("ADVISORBID", PathManager.getProperty("path.page.advisor-bid"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        page = redirectMap.getOrDefault(request.getParameter("to").toUpperCase(),
                PathManager.getProperty("path.page.error500"));

        if (page.equals(PathManager.getProperty("path.page.add-bid")))
            request.setAttribute("workType", WorkTypeService.getAllWorkType());

        return page;
    }
}
