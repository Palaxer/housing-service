package org.palax.dao;

import org.palax.entity.WorkPlane;

import java.util.List;

/**
 * The {@code WorkPlaneDao} interface for ORM database entity {@link WorkPlane}
 *
 * @author Taras Palashynskyy
 */

public interface WorkPlaneDao {

    /**
     * Method to get all {@link WorkPlane}
     *
     * @return return {@link List} of all {@link WorkPlane}
     */
    List<WorkPlane> getAllWorkPlane();

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@code  workType}
     *
     * @param workType it indicates an {@code  workType} that you want return
     * @return return {@link WorkPlane} by {@code workType}
     */
    List<WorkPlane> getAllWorkPlaneByWorkType(String workType);

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Brigade} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Brigade} {@code id}
     */
    List<WorkPlane> getAllWorkPlaneByBrigadeId(Long id);

    /**
     * Method return {@link WorkPlane} which find by {@code id}
     *
     * @param id it indicates an {@link WorkPlane} {@code id} that you want return
     * @return return {@link WorkPlane} by {@code id}
     */
    WorkPlane getWorkPlaneById(Long id);

    /**
     * Method returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     *
     * @param id it is a {@link org.palax.entity.Bid} {@code id}
     * @return returns {@link List} of {@link WorkPlane} which find by {@link org.palax.entity.Bid} {@code id}
     */
    WorkPlane getWorkPlaneByBidId(Long id);

    /**
     * Method delete {@link WorkPlane}
     *
     * @param workPlane {@code workPlane} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteWorkPlane(WorkPlane workPlane);

    /**
     * Method update {@link WorkPlane}
     *
     * @param workPlane the {@code workPlane} will update if it already exists
     * @return returns {@code true} if the {@code workPlane} updated
     *         or else {@code false}
     */
    boolean updateWorkPlane(WorkPlane workPlane);

    /**
     * Method to insert {@link WorkPlane}
     *
     * @param workPlane this {@code workPlane} will be inserted
     * @return returns {@code true} if {@link WorkPlane} inserted success
     *         or else {@code false}
     */
    boolean insertWorkPlane(WorkPlane workPlane);
}
