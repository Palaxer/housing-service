package org.palax.strategy;

import org.palax.entity.Bid;
import org.palax.service.BidService;

import java.util.List;

/**
 * The {@code GetNewBid} class which implements {@link GetBidStrategy} interface
 * and declares the method for receiving new bid
 *
 * @author Taras Palashynskyy
 * @see GetBidStrategy
 */

public class GetNewBid implements GetBidStrategy {

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of new {@link Bid}
     */
    @Override
    public List<Bid> getBid() {

        return  BidService.getAllBidByStatus("NEW");
    }
}
