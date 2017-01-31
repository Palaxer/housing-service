package org.palax.dao.util;

import org.palax.entity.Bid;
import org.palax.entity.User;
import org.palax.entity.WorkType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * {@code BidBuilder} class for building test data to {@link Bid} entity
 */
public class BidBuilder implements Builder<Bid> {

    private Bid bid;

    private BidBuilder() {
        bid = new Bid();
    }

    public static BidBuilder getBuilder() {
        return new BidBuilder();
    }

    public BidBuilder constructBid(Long template, String status, Builder<WorkType> workTypeBuilder, Builder<User> userBuilder) {
        if(template != null) {
            bid.setBidId(template);
            bid.setWorkScope(template);
            bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
            bid.setDescription("desc" + template);
            bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        }
        bid.setWorkType(workTypeBuilder != null ? workTypeBuilder.build() : null);
        bid.setUserTenant(userBuilder != null ? userBuilder.build() : null);
        bid.setStatus(status);

        return this;
    }

    @Override
    public Bid build() {
        return bid;
    }
}
