package org.palax.command;

import org.apache.log4j.Logger;
import org.palax.command.admin.*;
import org.palax.command.advisor.*;
import org.palax.command.auth.AuthenticationCommand;
import org.palax.command.auth.LoginCommand;
import org.palax.command.auth.LogoutCommand;
import org.palax.command.auth.RegistrationCommand;
import org.palax.command.crew.CompleteWorkCommand;
import org.palax.command.crew.GetWorkPlaneCommand;
import org.palax.command.info.BidInfoCommand;
import org.palax.command.info.BrigadeInfoCommand;
import org.palax.command.info.UserInfoCommand;
import org.palax.command.info.WorkPlaneInfoCommand;
import org.palax.command.setting.ChangePasswd;
import org.palax.command.setting.SetLocaleCommand;
import org.palax.command.setting.UpdateUserProfileCommand;
import org.palax.command.tenant.AddTenantBidCommand;
import org.palax.command.tenant.CloseBidCommand;
import org.palax.command.tenant.GetTenantBidCommand;
import org.palax.command.util.RedirectCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CommandHelper} a class which, depending on the query parameter
 * and selects the page to redirect
 *
 * @author Taras Palashynskyy
 */

public class CommandHelper {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CommandHelper.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static CommandHelper commandHelper;
    /**Map that stores the string representation of commands and their instance */
    private final Map<String, Command> commandMapping;

    /**
     * Constructor which initialize {@code commandMapping}
     */
    private CommandHelper() {
        logger.debug("Initialization command mapping");
        commandMapping = new HashMap<>();
        commandMapping.put("TENANTBID", new GetTenantBidCommand());
        commandMapping.put("LOGIN" , new LoginCommand());
        commandMapping.put("REGISTRATION", new RegistrationCommand());
        commandMapping.put("REDIRECT", new RedirectCommand());
        commandMapping.put("LOGOUT", new LogoutCommand());
        commandMapping.put("CLOSEBID", new CloseBidCommand());
        commandMapping.put("ADDBID", new AddTenantBidCommand());
        commandMapping.put("SETLOCALE", new SetLocaleCommand());
        commandMapping.put("UPDATEUSER", new UpdateUserProfileCommand());
        commandMapping.put("CHANGEPASSWD", new ChangePasswd());
        commandMapping.put("ADVISORBID", new GetBidCommand());
        commandMapping.put("ADVISORWORKPLANE", new GetAllWorkPlaneCommand());
        commandMapping.put("BIDINFO", new BidInfoCommand());
        commandMapping.put("SETBRIGADE", new SetBrigadeCommand());
        commandMapping.put("CREWWORKPLANE", new GetWorkPlaneCommand());
        commandMapping.put("WORKPLANEINFO", new WorkPlaneInfoCommand());
        commandMapping.put("COMPLETEWORK", new CompleteWorkCommand());
        commandMapping.put("BRIGADEINFO", new BrigadeInfoCommand());
        commandMapping.put("GETWORKTYPE", new GetAllWorkTypeCommand());
        commandMapping.put("ADDWORKTYPE", new AddWorkTypeCommand());
        commandMapping.put("UPDATEWORKTYPE", new UpdateWorkTypeCommand());
        commandMapping.put("GETBRIGADE", new GetAllBrigadeCommand());
        commandMapping.put("ADDBRIGADE", new AddBrigadeCommand());
        commandMapping.put("GETALLUSER", new GetAllUserCommand());
        commandMapping.put("USERINFO", new UserInfoCommand());
        commandMapping.put("ADMUPDATEUSER", new UpdateUserCommand());
    }

    /**
     * Always return same {@link CommandHelper} instance
     *
     * @return always return same {@link CommandHelper} instance
     */
    public static CommandHelper getInstance() {
        if (commandHelper == null)
            commandHelper = new CommandHelper();
        return commandHelper;
    }

    /**
     *
     *
     * @param request {@code request} must contain a {@code "command"} parameter
     * @return return {@link Command} instance which corresponds to the transmitted command
     */
    public Command getCommand(HttpServletRequest request) {
        Command current = new AuthenticationCommand();

        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            logger.info("The request doesn`t pass command " + request.getMethod() + " " + request.getRequestURI());
            return current;
        }

        logger.info("The request pass command " + request.getMethod() + " " + action.toUpperCase());
        current = commandMapping.get(action.toUpperCase());

        return current;
    }

}
