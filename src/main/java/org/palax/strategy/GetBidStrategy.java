package org.palax.strategy;

import org.palax.entity.Bid;

import java.util.List;

/**
 * The {@code GetBidStrategy} interface which used to implementation strategy pattern
 * and declares the method for receiving bid
 *
 * @author Taras Palashynskyy
 */

public interface GetBidStrategy {

    /**
     * Method return {@link List} of {@link Bid} by some strategy
     *
     * @return return {@link List} of {@link Bid}
     */
    List<Bid> getBid();
}
