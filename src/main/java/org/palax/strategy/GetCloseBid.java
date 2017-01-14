package org.palax.strategy;

import org.palax.entity.Bid;
import org.palax.service.BidService;

import java.util.List;

/**
 * The {@code GetCloseBid} class which implements {@link GetBidStrategy} interface
 * and declares the method for receiving close bid
 *
 * @author Taras Palashynskyy
 * @see GetBidStrategy
 */

public class GetCloseBid implements GetBidStrategy {

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of close {@link Bid}
     */
    @Override
    public List<Bid> getBid() {

        return  BidService.getAllBidByStatus("CLOSE");
    }
}
