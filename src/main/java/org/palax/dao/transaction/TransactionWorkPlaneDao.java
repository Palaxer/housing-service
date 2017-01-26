package org.palax.dao.transaction;

import org.palax.entity.WorkPlane;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code WorkPlaneDao} interface for ORM database entity {@link WorkPlane} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionWorkPlaneDao {

    /**
     * Method to get all {@link WorkPlane}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link WorkPlane}
     */
    List<WorkPlane> getAllWorkPlaneTransaction(Connection con);

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@code  workType}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workType it indicates an {@code  workType} that you want return
     * @return return {@link WorkPlane} by {@code workType}
     */
    List<WorkPlane> getAllWorkPlaneByWorkTypeTransaction(String workType, Connection con);

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    List<WorkPlane> getAllWorkPlaneByBrigadeIdTransaction(Long id, Connection con);

    /**
     * Method return {@link WorkPlane} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it indicates an {@link WorkPlane} {@code id} that you want return
     * @return return {@link WorkPlane} by {@code id}
     */
    WorkPlane getWorkPlaneByIdTransaction(Long id, Connection con);

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it is a {@link org.palax.entity.Bid} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     */
    WorkPlane getWorkPlaneByBidIdTransaction(Long id, Connection con);

    /**
     * Method delete {@link WorkPlane}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workPlane {@code workPlane} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteWorkPlaneTransaction(WorkPlane workPlane, Connection con);

    /**
     * Method update {@link WorkPlane}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workPlane the {@code workPlane} will update if it already exists
     * @return returns {@code true} if the {@code workPlane} updated
     *         or else {@code false}
     */
    boolean updateWorkPlaneTransaction(WorkPlane workPlane, Connection con);

    /**
     * Method to insert {@link WorkPlane}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workPlane this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link WorkPlane} inserted success
     *         or else {@code false}
     */
    boolean insertWorkPlaneTransaction(WorkPlane workPlane, Connection con);
}
