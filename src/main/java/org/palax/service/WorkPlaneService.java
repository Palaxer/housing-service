package org.palax.service;

import org.apache.log4j.Logger;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.factory.TransactionMySQLDAOFactory;
import org.palax.entity.Brigade;
import org.palax.entity.WorkPlane;
import org.palax.utils.DataSourceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BidService} service is a convenient API for working with the {@link org.palax.dao.WorkPlaneDao}
 *
 * @author Taras Palashynskyy
 */

public class WorkPlaneService {

    private static final Logger logger = Logger.getLogger(WorkPlaneService.class);

    private WorkPlaneService() {

    }

    /**
     * Method to get all {@link WorkPlane}
     *
     * @return return {@link List} of all {@link WorkPlane}
     */
    public static List<WorkPlane> getAllWorkPlane(){

        return MySQLDAOFactory.getWorkPlaneDao().getAllWorkPlane();
    }

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    public static List<WorkPlane> getAllWorkPlaneByBrigadeId(Long id) {
        List<WorkPlane> workPlaneList =  new ArrayList<>();

        for (WorkPlane el : MySQLDAOFactory.getWorkPlaneDao().getAllWorkPlaneByBrigadeId(id)) {
            if(!el.getStatus().equals("COMPLETE"))
                workPlaneList.add(el);
        }

        return workPlaneList;
    }

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Bid} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     */
    public static WorkPlane getWorkPlaneByBidId(Long id) {

        return MySQLDAOFactory.getWorkPlaneDao().getWorkPlaneByBidId(id);
    }

    /**
     * Method return {@link WorkPlane} which find by {@code id}
     *
     * @param id it indicates an {@link WorkPlane} {@code id} that you want return
     * @return return {@link WorkPlane} by {@code id}
     */
    public static WorkPlane getWorkPlaneById(Long id) {

        return MySQLDAOFactory.getWorkPlaneDao().getWorkPlaneById(id);
    }

    /**
     * Method add {@link WorkPlane} if {@code brigade} may perform {@code bid}
     * and set {@code brigade} to this {@code workPlane} reference
     *
     * @param brigade you want to assign a {@code brigade}
     * @param workPlane this {@code workPlane} will be added
     * @return returns {@code true} if {@link WorkPlane} add success
     *         and {@code brigade} may perform {@code bid}
     *         or else {@code false}
     */
    public static boolean setBrigadeToWorkPlane(Brigade brigade, WorkPlane workPlane) {
        Boolean result = false;
        if(brigade == null)
            return result;

        if(BrigadeService.datatimeValid(brigade, workPlane, 20)) {
            Connection con = DataSourceManager.getConnection();

            try {
                con.setAutoCommit(false);

                workPlane.setBrigade(brigade);

                if(TransactionMySQLDAOFactory.getWorkPlaneDao().insertWorkPlaneTransaction(workPlane, con)) {
                    workPlane.getBid().setStatus("IN WORK");
                    if(TransactionMySQLDAOFactory.getBidDao().updateBidTransaction(workPlane.getBid(), con)) {
                        con.commit();
                        result = true;
                    } else {
                        con.rollback();
                        logger.debug("Statement was roll back");
                    }
                }

                con.setAutoCommit(true);

            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:", e);
            } finally {
                DataSourceManager.closeAll(con, null, null);
            }
        }

        return result;
    }

    /**
     * Method change the {@code status} to {@code COMPLETE} of the {@link WorkPlane} results by {@code id}
     *
     * @param id it indicates an {@link WorkPlane} {@code id} that you want change
     * @return returns {@code true} if the status is changed
     *         or else {@code false}
     */
    public static boolean completeWorkPlaneById(Long id) {
        WorkPlane workPlane = MySQLDAOFactory.getWorkPlaneDao().getWorkPlaneById(id);

        Boolean result = false;
        Connection con = DataSourceManager.getConnection();
        try {
            con.setAutoCommit(false);

            workPlane.setStatus("COMPLETE");
            workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));

            if(TransactionMySQLDAOFactory.getWorkPlaneDao().updateWorkPlaneTransaction(workPlane, con)) {
                workPlane.getBid().setStatus("COMPLETE");
                if(TransactionMySQLDAOFactory.getBidDao().updateBidTransaction(workPlane.getBid(), con)) {
                    con.commit();
                    result = true;
                } else {
                    con.rollback();
                    logger.debug("Statement was roll back");
                }
            }

            con.setAutoCommit(true);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        } finally {
            DataSourceManager.closeAll(con, null, null);
        }

        return result;
    }
}
