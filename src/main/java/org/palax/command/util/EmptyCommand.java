package org.palax.command.util;

import org.palax.command.Command;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code EmptyCommand} class implements {@link Command} used in case of incorrect or blank command
 *
 * @author Taras Palashynskyy
 */

public class EmptyCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.error404");

        return page;
    }
}
