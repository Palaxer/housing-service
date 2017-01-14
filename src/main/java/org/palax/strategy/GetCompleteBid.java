package org.palax.strategy;

import org.palax.entity.Bid;
import org.palax.service.BidService;

import java.util.List;

/**
 * The {@code GetCompleteBid} class which implements {@link GetBidStrategy} interface
 * and declares the method for receiving complete bid
 *
 * @author Taras Palashynskyy
 * @see GetBidStrategy
 */

public class GetCompleteBid implements GetBidStrategy {

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of complete {@link Bid}
     */
    @Override
    public List<Bid> getBid() {

        return  BidService.getAllBidByStatus("COMPLETE");
    }
}
