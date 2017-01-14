package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Brigade;
import org.palax.entity.WorkPlane;

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
        if(brigade == null)
            return false;

        if(BrigadeService.datatimeValid(brigade, workPlane, 20)) {
            workPlane.setBrigade(brigade);
            return MySQLDAOFactory.getWorkPlaneDao().insertWorkPlane(workPlane);
        }

        return false;
    }

    /**
     * Method change the {@code status} of the {@link WorkPlane} results by {@code id}
     *
     * @param id it indicates an {@link WorkPlane} {@code id} that you want change
     * @param status this value will change the {@code status} of the {@link WorkPlane}
     * @return returns {@code true} if the status is changed
     *         or else {@code false}
     */
    public static boolean changeWorkPlaneStatusById(String status, Long id) {
        WorkPlane workPlane = MySQLDAOFactory.getWorkPlaneDao().getWorkPlaneById(id);

        workPlane.setStatus(status);
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));

        return MySQLDAOFactory.getWorkPlaneDao().updateWorkPlane(workPlane);
    }
}
