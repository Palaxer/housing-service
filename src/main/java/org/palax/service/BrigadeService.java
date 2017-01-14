package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Bid;
import org.palax.entity.Brigade;
import org.palax.entity.WorkPlane;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * The {@code BrigadeService} service is a convenient API for working with the {@link org.palax.dao.BrigadeDao}
 *
 * @author Taras Palashynskyy
 */

public class BrigadeService {

    private BrigadeService() {

    }

    /**
     * Method finds any {@link Brigade} which can perform the specified {@code bid}
     *
     * @param bid the {@link Bid} to which the {@link Brigade} is found that can do the job
     * @param time desired the job time
     * @param minPerWorkScope it indicates how many minutes takes per one {@code workScope}
     *                          example 10 = 10 minutes; 50 = 50 minutes; 1 = 1 minutes
     *                                  if {@code workScope} would be 10 and {@code minPerWorkScope} 20
     *                                  then 10 * 20 = 200 minutes need to complete job
     * @return returns {@link List} of the {@link Brigade} which can perform {@code bid}
     */
    public static List<Brigade> getMatchBrigade(Bid bid, LocalDateTime time, int minPerWorkScope) {
        List<Brigade> brigadeList = MySQLDAOFactory.getBrigadeDao().getAllBrigadeByWorkType(bid.getWorkType().getTypeName());

        List<WorkPlane> workPlaneList = MySQLDAOFactory.getWorkPlaneDao().getAllWorkPlaneByWorkType(bid.getWorkType().getTypeName());

        for (WorkPlane el : workPlaneList) {
            long period = ChronoUnit.MINUTES.between(el.getWorkTime().toLocalDateTime(), time);
            long workScope = el.getBid().getWorkScope();
            if(!(period >= (minPerWorkScope * workScope) ||
                    period <= -(minPerWorkScope * workScope + minPerWorkScope * bid.getWorkScope()))) {
                brigadeList.remove(el.getBrigade());
            }
        }

        return brigadeList;
    }

    /**
     * Method which checks whether the {@code brigade} will be able to do the {@code bid} at a specified time
     *
     * @param brigade a {@code brigade} that is checked
     * @param workPlane {@code workPlane} which keeps both the {@code bid} and the {@code leadTime} at which
     *                  a {@code brigade} will be tested
     * @param minPerWorkScope it indicates how many minutes takes per one {@code workScope}
     *                          example 10 = 10 minutes; 50 = 50 minutes; 1 = 1 minutes
     *                                  if {@code workScope} would be 10 and {@code minPerWorkScope} 20
     *                                  then 10 * 20 = 200 minutes need to complete job
     * @return  returns {@code true} if the status is changed
     *         or else {@code false}
     */
    public static boolean datatimeValid(Brigade brigade, WorkPlane workPlane, int minPerWorkScope) {

        List<WorkPlane> workPlaneList = MySQLDAOFactory.getWorkPlaneDao().getAllWorkPlaneByWorkType(workPlane.getBid()
                .getWorkType().getTypeName());

        for (WorkPlane el : workPlaneList) {
            long period = ChronoUnit.HOURS.between(el.getWorkTime().toLocalDateTime(),
                    workPlane.getWorkTime().toLocalDateTime());
            long workScope = el.getBid().getWorkScope();
            if(!(period >= (minPerWorkScope * workScope) ||
                    period <= -(minPerWorkScope * workScope + minPerWorkScope * workPlane.getBid().getWorkScope()))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method return {@link Brigade} which find by {@code id}
     *
     * @param id it indicates an {@link Brigade} {@code id} that you want return
     * @return return {@link Brigade} by {@code id}
     */
    public static Brigade getBrigadeById(Long id) {
        return MySQLDAOFactory.getBrigadeDao().getBrigadeById(id);
    }

    /**
     * Method to get all {@link Brigade}
     *
     * @return return {@link List} of all {@link Brigade}
     */
    public static List<Brigade> getAllBrigade(){
        return MySQLDAOFactory.getBrigadeDao().getAllBrigade();
    }

    /**
     * Method to add {@link Brigade}
     *
     * @param brigade this {@code brigade} will be added
     * @return returns {@code true} if {@link Brigade} add success
     *         or else {@code false}
     */
    public static boolean addBrigade(Brigade brigade) {
        return MySQLDAOFactory.getBrigadeDao().insertBrigade(brigade);
    }
}
