package org.palax.dao.transaction;

import org.palax.entity.Bid;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code TransactionBidDao} interface for ORM database entity {@link Bid} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionBidDao {

    /**
     * Method to get all {@link Bid}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link Bid}
     */
    List<Bid> getAllBidTransaction(Connection con);

    /**
     * Method returns {@link List} of {@link Bid} which find by {@code  status}
     *
     * @param con {@code con} this connection that enables transactional
     * @param status it indicates an {@code  status} that you want return
     * @return return {@link Bid} by {@code status}
     */
    List<Bid> getAllBidByStatusTransaction(String status, Connection con);

    /**
     * Method returns {@link List} of {@link Bid} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it is a {@link org.palax.entity.User} {@code id}
     * @return returns {@link List} of {@link Bid} which find by {@link org.palax.entity.User} {@code id}
     */
    List<Bid> getAllBidByUserTenantTransaction(Long id, Connection con);

    /**
     * Method return {@link Bid} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it indicates an {@link Bid} {@code id} that you want return
     * @return return {@link Bid} by {@code id}
     */
    Bid getBidByIdTransaction(Long id, Connection con);

    /**
     * Method delete {@link Bid}
     *
     * @param con {@code con} this connection that enables transactional
     * @param bid {@code bid} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteBidTransaction(Bid bid, Connection con);

    /**
     * Method update {@link Bid}
     *
     * @param con {@code con} this connection that enables transactional
     * @param bid the {@code bid} will update if it already exists
     * @return returns {@code true} if the {@code bid} updated
     *         or else {@code false}
     */
    boolean updateBidTransaction(Bid bid, Connection con);

    /**
     * Method to insert {@link Bid}
     *
     * @param con {@code con} this connection that enables transactional
     * @param bid this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link Bid} inserted success
     *         or else {@code false}
     */
    boolean insertBidTransaction(Bid bid, Connection con);

}
