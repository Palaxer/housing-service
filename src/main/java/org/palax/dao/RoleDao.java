package org.palax.dao;

import org.palax.entity.Role;

import java.util.List;

/**
 * The {@code RoleDao} interface for ORM database entity {@link Role}
 *
 * @author Taras Palashynskyy
 */

public interface RoleDao {

    /**
     * Method to get all {@link Role}
     *
     * @return return {@link List} of all {@link Role}
     */
    List<Role> getAllRole();

    /**
     * Method return {@link Role} which find by {@code name}
     *
     * @param name it indicates an {@link UserDao} {@code name} that you want return
     * @return return {@link Role} by {@code name}
     */
    Role getRoleByName(String name);

    /**
     * Method return {@link Role} which find by {@code id}
     *
     * @param id it indicates an {@link UserDao} {@code id} that you want return
     * @return return {@link Role} by {@code id}
     */
    Role getRoleById(Long id);

    /**
     * Method delete {@link Role}
     *
     * @param role {@code workPlane} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteRole(Role role);

    /**
     * Method update {@link Role}
     *
     * @param role the {@code role} will update if it already exists
     * @return returns {@code true} if the {@code role} updated
     *         or else {@code false}
     */
    boolean updateRole(Role role);

    /**
     * Method to insert {@link Role}
     *
     * @param role this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link Role} inserted success
     *         or else {@code false}
     */
    boolean insertRole(Role role);
}
