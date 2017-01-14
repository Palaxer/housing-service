package org.palax.strategy;

import org.palax.entity.Bid;
import org.palax.service.BidService;

import java.util.List;

/**
 * The {@code GetInWorkBid} class which implements {@link GetBidStrategy} interface
 * and declares the method for receiving in work bid
 *
 * @author Taras Palashynskyy
 * @see GetBidStrategy
 */

public class GetInWorkBid implements GetBidStrategy {

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of in work {@link Bid}
     */
    @Override
    public List<Bid> getBid() {

        return  BidService.getAllBidByStatus("IN WORK");
    }
}
