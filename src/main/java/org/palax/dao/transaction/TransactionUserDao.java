package org.palax.dao.transaction;

import org.palax.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code UserDao} interface for ORM database entity {@link User} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionUserDao {

    /**
     * Method to get all {@link User}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link User}
     */
    List<User> getAllUserTransaction(Connection con);

    /**
     * Method returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    List<User> getAllUserByBrigadeIdTransacion(Long id, Connection con);

    /**
     * Method return {@link User} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it indicates an {@link User} {@code id} that you want return
     * @return return {@link User} by {@code id}
     */
    User getUserByIdTransaction(Long id, Connection con);

    /**
     * Method return {@link User} which find by {@code login}
     *
     * @param con {@code con} this connection that enables transactional
     * @param login it indicates an {@link User} {@code login} that you want return
     * @return return {@link User} by {@code login}
     */
    User getUserByLoginTransaction(String login, Connection con);

    /**
     * Method delete {@link User}
     *
     * @param con {@code con} this connection that enables transactional
     * @param user {@code user} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteUserTransaction(User user, Connection con);

    /**
     * Method update {@link User}
     *
     * @param con {@code con} this connection that enables transactional
     * @param user the {@code workPlane} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean updateUserTransaction(User user, Connection con);

    /**
     * Method to insert {@link User}
     *
     * @param con {@code con} this connection that enables transactional
     * @param user this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link User} inserted success
     *         or else {@code false}
     */
    boolean insertUserTransaction(User user, Connection con);
}
