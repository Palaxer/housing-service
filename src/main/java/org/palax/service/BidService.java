package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Bid;
import org.palax.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@code BidService} service is a convenient API for working with the {@link org.palax.dao.BidDao}
 *
 * @author Taras Palashynskyy
 */

public class BidService {

    private BidService() {

    }

    /**
     * Method to get all {@link Bid}
     *
     * @return return {@link List} of all {@link Bid}
     */
    public static List<Bid> getAllBid() {

        return MySQLDAOFactory.getBidDao().getAllBid();
    }

    /**
     * Method returns {@link List} of all the {@code user} {@link Bid}
     *
     * @param user indicates the bids of the {@code user} to return
     * @return return {@link List} of all the {@code user} {@link Bid}
     */
    public static List<Bid> getAllBidByUserID(User user) {

        return MySQLDAOFactory.getBidDao().getAllBidByUserTenant(user.getUserId());
    }

    /**
     * Method returns {@link List} of all {@link Bid} which find by {@code status}
     *
     * @param status it indicates the {@code status} of the bids that you want to return
     * @return return {@link List} of all {@link Bid} by {@code status}
     */
    public static List<Bid> getAllBidByStatus(String status) {

        return MySQLDAOFactory.getBidDao().getAllBidByStatus(status);
    }

    /**
     * Method return {@link Bid} which find by {@code id}
     *
     * @param id it indicates an {@link Bid} {@code id} that you want return
     * @return return {@link Bid} by {@code id}
     */
    public static Bid getBidByID(Long id) {

        return MySQLDAOFactory.getBidDao().getBidById(id);
    }

    /**
     * Method change the {@code status} of the {@link Bid} results by {@code id}
     *
     * @param id it indicates an {@link Bid} {@code id} that you want change
     * @param status this value will change the {@code status} of the {@link Bid}
     * @return returns {@code true} if the status is changed
     *         or else {@code false}
     */
    public static boolean changeStatusBid(Long id, String status) {

        Bid bid = MySQLDAOFactory.getBidDao().getBidById(id);

        bid.setStatus(status);

        return MySQLDAOFactory.getBidDao().updateBid(bid);
    }

    /**
     * Method to add {@link Bid}
     *
     * @param bid this {@code bid} will be added
     * @return returns {@code true} if {@link Bid} add success
     *         or else {@code false}
     */
    public static boolean addBid(Bid bid) {

        return MySQLDAOFactory.getBidDao().insertBid(bid);
    }

    /**
     * Method which validate the {@link Bid} {@code workScope}
     *
     * @param workScope {@link Bid} {@code workScope} which need validate
     * @return returns {@code true} if {@link Bid} {@code workScope} between 1 to 10
     *         or else {@code false}
     */
    public static boolean workScopeValid(Long workScope) {
        return (workScope >= 0) && (workScope <= 10);
    }

    /**
     * Method which validate the {@link Bid} {@code leadTime}
     *
     * @param leadTime {@link Bid} {@code leadTime} which will validate
     * @param day {@code day} set how many days you need to miss
     * @return returns {@code true} if the {@link Bid} {@code leadTime}
     *         is after a {@code day} from {@code LocalDateTime.now()}
     *         or else {@code false}
     */
    public static boolean leadTimeValid(Timestamp leadTime, Long day) {
        return leadTime.after(Timestamp.valueOf(LocalDateTime.now().plusDays(day)));
    }

}
