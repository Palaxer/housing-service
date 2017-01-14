package org.palax.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code Command} interface used for command pattern
 *
 * @author Taras Palashynskyy
 */

public interface Command {

    /**
     * Method execute the necessary business logic and services
     *
     * @param request a {@link HttpServletRequest} which will then be filled with all necessary attributes
     * @param response {@link HttpServletResponse}
     * @return returns the path to the page that is ready to forward
     */
    String execute(HttpServletRequest request, HttpServletResponse response);

}
