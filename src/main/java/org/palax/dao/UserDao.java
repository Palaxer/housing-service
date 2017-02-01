package org.palax.dao;

import org.palax.entity.User;

import java.util.List;

/**
 * The {@code UserDao} interface for ORM database entity {@link User}
 *
 * @author Taras Palashynskyy
 */

public interface UserDao {

    /**
     * Method to get all {@link User}
     *
     * @return return {@link List} of all {@link User}
     */
    List<User> getAllUser();

    List<User> getAllUser(int offSet, int numberOfElement);

    Long getTableRowSize();

    /**
     * Method returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link User} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    List<User> getAllUserByBrigadeId(Long id);

    /**
     * Method return {@link User} which find by {@code id}
     *
     * @param id it indicates an {@link User} {@code id} that you want return
     * @return return {@link User} by {@code id}
     */
    User getUserById(Long id);

    /**
     * Method return {@link User} which find by {@code login}
     *
     * @param login it indicates an {@link User} {@code login} that you want return
     * @return return {@link User} by {@code login}
     */
    User getUserByLogin(String login);

    /**
     * Method delete {@link User}
     *
     * @param user {@code user} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteUser(User user);

    /**
     * Method update {@link User}
     *
     * @param user the {@code workPlane} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean updateUser(User user);

    /**
     * Method to insert {@link User}
     *
     * @param user this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link User} inserted success
     *         or else {@code false}
     */
    boolean insertUser(User user);
}
