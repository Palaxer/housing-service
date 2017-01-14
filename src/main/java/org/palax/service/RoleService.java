package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Role;

import java.util.List;

/**
 * The {@code RoleService} service is a convenient API for working with the {@link org.palax.dao.RoleDao}
 *
 * @author Taras Palashynskyy
 */

public class RoleService {

    private RoleService() {

    }

    /**
     * Method returns all {@link Role} which has in the system
     *
     * @return returns {@link List} of all {@link Role}
     */
    public static List<Role> getAllRole() {

        return MySQLDAOFactory.getRoleDao().getAllRole();
    }

    /**
     *  Method return {@link Role} which find by {@code roleType}
     *
     * @param roleType specifies the {@code roleType} of the {@link Role} you want to search
     * @return return {@link Role} which find by {@code roleType}
     */
    public static Role getRoleByName(String roleType) {

        return MySQLDAOFactory.getRoleDao().getRoleByName(roleType);
    }
}
