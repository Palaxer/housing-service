package org.palax.strategy;

import org.palax.entity.Bid;
import org.palax.service.BidService;

import java.util.List;

/**
 * The {@code GetAllBid} class which implements {@link GetBidStrategy} interface
 * and declares the method for receiving all bid
 *
 * @author Taras Palashynskyy
 * @see GetBidStrategy
 */

public class GetAllBid implements GetBidStrategy {

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of all {@link Bid}
     */
    @Override
    public List<Bid> getBid() {

        return BidService.getAllBid();
    }
}
