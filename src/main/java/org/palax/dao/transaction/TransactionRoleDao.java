package org.palax.dao.transaction;

import org.palax.entity.Role;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code RoleDao} interface for ORM database entity {@link Role} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionRoleDao {

    /**
     * Method to get all {@link Role}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link Role}
     */
    List<Role> getAllRoleTransaction(Connection con);

    /**
     * Method return {@link Role} which find by {@code name}
     *
     * @param con {@code con} this connection that enables transactional
     * @param name it indicates an {@link Role} {@code name} that you want return
     * @return return {@link Role} by {@code name}
     */
    Role getRoleByNameTransaction(String name, Connection con);

    /**
     * Method return {@link Role} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it indicates an {@link Role} {@code id} that you want return
     * @return return {@link Role} by {@code id}
     */
    Role getRoleByIdTransaction(Long id, Connection con);

    /**
     * Method delete {@link Role}
     *
     * @param con {@code con} this connection that enables transactional
     * @param role {@code workPlane} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteRoleTransaction(Role role, Connection con);

    /**
     * Method update {@link Role}
     *
     * @param con {@code con} this connection that enables transactional
     * @param role the {@code role} will update if it already exists
     * @return returns {@code true} if the {@code role} updated
     *         or else {@code false}
     */
    boolean updateRoleTransaction(Role role, Connection con);

    /**
     * Method to insert {@link Role}
     *
     * @param con {@code con} this connection that enables transactional
     * @param role this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link Role} inserted success
     *         or else {@code false}
     */
    boolean insertRoleTransaction(Role role, Connection con);
}
