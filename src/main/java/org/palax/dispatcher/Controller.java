package org.palax.dispatcher;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.CommandHelper;
import org.palax.utils.PathManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code Controller} class extends {@link HttpServlet} is a controller that receives
 * and forwards all requests or redirect to the desired page
 *
 * @author Taras Palashynskyy
 */

public class Controller extends HttpServlet {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(Controller.class);

    /**
     *  {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method of processing all requests
     *
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws ServletException {@link ServletException}
     * @throws IOException {@link IOException}
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;

        Command command = CommandHelper.getInstance().getCommand(request);

        page = command.execute(request, response);

        if(page != null) {
            if(page.contains("jsp")) {
                logger.info("Forward to " + page);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                logger.info("Forward to " + page);
                response.sendRedirect(request.getContextPath() + page);
            }
        } else {
            page = PathManager.getProperty("path.page.error500");
            logger.info("Forward to " + page);
            response.sendRedirect(request.getContextPath() + page);
        }

    }
}
