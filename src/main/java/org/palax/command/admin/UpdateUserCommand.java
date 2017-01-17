package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.setting.UpdateUserProfileCommand;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dto.InvalidData;
import org.palax.entity.Brigade;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.BrigadeService;
import org.palax.service.RoleService;
import org.palax.service.UserPrincipalService;
import org.palax.service.UserService;
import org.palax.utils.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UpdateUserCommand} class implements {@link Command}
 * used for update information about user
 *
 * @author Taras Palashynskyy
 */

public class UpdateUserCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static Logger logger = Logger.getLogger(UpdateUserProfileCommand.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.user-info");

        User user = (User) request.getSession().getAttribute("user");

        if(!UserPrincipalService.permission(user, "path.page.user-info"))
            return PathManager.getProperty("path.page.error-perm");

        try {
            User updateUser = updateUser = UserService.getUserById(Long.parseLong(request.getParameter("index")));

            updateUser.setFirstName(request.getParameter("first-name"));
            updateUser.setLastName(request.getParameter("last-name"));
            Role role = RoleService.getRoleByName(request.getParameter("role"));
            updateUser.setRole(role);
            updateUser.setPosition(request.getParameter("position").isEmpty() ? null : request.getParameter("position"));
            String brigadeId = request.getParameter("brigade");
            Brigade brigade = null;
            if(!brigadeId.equals("null")) {
                brigade = MySQLDAOFactory.getBrigadeDao().getBrigadeById(Long.parseLong(brigadeId));
            }
            updateUser.setBrigade(brigade);
            updateUser.setStreet(request.getParameter("street"));
            updateUser.setHouseNumber(request.getParameter("house-number"));
            updateUser.setCity(request.getParameter("city"));
            updateUser.setPhoneNumber(request.getParameter("phone-number"));

            updateUser.setApartment(Long.parseLong(request.getParameter("apartment")));

            InvalidData.Builder builder = InvalidData.newBuilder("invalid");
            boolean flag = false;

            if (!UserService.firstNameValid(updateUser.getFirstName())) {
                builder.setInvalidFirstNameAttr();
                flag = true;
            }
            if (!UserService.lastNameValid(updateUser.getLastName())) {
                builder.setInvalidLastNameAttr();
                flag = true;
            }
            if (!UserService.streetValid(updateUser.getStreet())) {
                builder.setInvalidStreetAttr();
                flag = true;
            }
            if (!UserService.houseNumberValid(updateUser.getHouseNumber())) {
                builder.setInvalidHouseAttr();
                flag = true;
            }
            if (!UserService.apartmentValid(updateUser.getApartment())) {
                builder.setInvalidApartment();
                flag = true;
            }
            if (!UserService.cityValid(updateUser.getCity())) {
                builder.setInvalidCityAttr();
                flag = true;
            }
            if (!UserService.phoneNumberValid(updateUser.getPhoneNumber())) {
                builder.setInvalidPhoneNumber();
                flag = true;
            }

            if (flag)
                request.setAttribute("invalidData", builder.build());
            else if (UserService.updateUser(updateUser)) {
                request.setAttribute("successUpdate", true);
                if(user.getUserId().equals(updateUser.getUserId()))
                    UserService.userDuplicate(updateUser, user);
            } else
                request.setAttribute("invalidUpdate", true);


            request.setAttribute("user", updateUser);
            request.setAttribute("roleList", RoleService.getAllRole());
            request.setAttribute("brigadeList", BrigadeService.getAllBrigade());


        } catch (NumberFormatException e) {
            logger.debug("Threw a NumberFormatException, full stack trace follows:",e);
        }


        return page;
    }
}
