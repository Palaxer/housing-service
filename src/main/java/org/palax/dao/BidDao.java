package org.palax.dao;

import org.palax.entity.Bid;

import java.util.List;

/**
 * The {@code BidDao} interface for ORM database entity {@link Bid}
 *
 * @author Taras Palashynskyy
 */

public interface BidDao {

    /**
     * Method to get all {@link Bid}
     *
     * @return return {@link List} of all {@link Bid}
     */
    List<Bid> getAllBid();

    /**
     * Method returns {@link List} of {@link Bid} which find by {@code  status}
     *
     * @param status it indicates an {@code  status} that you want return
     * @return return {@link Bid} by {@code status}
     */
    List<Bid> getAllBidByStatus(String status);

    /**
     * Method returns {@link List} of {@link Bid} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param id it is a {@link org.palax.entity.User} {@code id}
     * @return returns {@link List} of {@link Bid} which find by {@link org.palax.entity.User} {@code id}
     */
    List<Bid> getAllBidByUserTenant(Long id);

    /**
     * Method return {@link Bid} which find by {@code id}
     *
     * @param id it indicates an {@link Bid} {@code id} that you want return
     * @return return {@link Bid} by {@code id}
     */
    Bid getBidById(Long id);

    /**
     * Method delete {@link Bid}
     *
     * @param bid {@code bid} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteBid(Bid bid);

    /**
     * Method update {@link Bid}
     *
     * @param bid the {@code bid} will update if it already exists
     * @return returns {@code true} if the {@code bid} updated
     *         or else {@code false}
     */
    boolean updateBid(Bid bid);

    /**
     * Method to insert {@link Bid}
     *
     * @param bid this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link Bid} inserted success
     *         or else {@code false}
     */
    boolean insertBid(Bid bid);

}
