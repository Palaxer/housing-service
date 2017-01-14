package org.palax.strategy;

import org.palax.entity.Bid;

import java.util.List;

/**
 * The {@code GetBid} is a context class which provide a way to set
 * which strategy will used to get {@link Bid}
 *
 * @author Taras Palashynskyy
 */

public class GetBid {
    /**Object that stores an instance of a necessary strategy. */
    private GetBidStrategy strategy;

    /**
     * Constructor which sets the necessary strategy
     *
     * @param strategy instance which implements {@link GetBidStrategy} interface
     */
    public GetBid(GetBidStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method return {@link List} of {@link Bid} by some strategy
     *
     * @return return {@link List} of {@link Bid}
     */
    public List<Bid> getBid() {
        return strategy.getBid();
    }
}
