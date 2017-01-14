package org.palax.dao;

import org.palax.entity.WorkType;

import java.util.List;

/**
 * The {@code WorkTypeDao} interface for ORM database entity {@link WorkType}
 *
 * @author Taras Palashynskyy
 */

public interface WorkTypeDao {
    /**
     * Method to get all {@link WorkType}
     *
     * @return return {@link List} of all {@link WorkType}
     */
    List<WorkType> getAllWorkType();

    /**
     *  Method return {@link WorkType} which find by {@code name}
     *
     * @param name specifies the {@code name} of the {@link WorkType} you want to search
     * @return return {@link WorkType} which find by {@code name}
     */
    WorkType getWorkTypeByName(String name);

    /**
     * Method return {@link WorkType} which find by {@code id}
     *
     * @param id specifies the {@code id} of the {@link WorkType} you want to search
     * @return return {@link WorkType} which find by {@code name}
     */
    WorkType getWorkTypeById(Long id);

    /**
     * Method delete {@link WorkType}
     *
     * @param workType {@code workType} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteWorkType(WorkType workType);

    /**
     * Method update {@link WorkType}
     *
     * @param workType the {@code workType} will update if it already exists
     * @return returns {@code true} if the {@code workType} updated
     *         or else {@code false}
     */
    boolean updateWorkType(WorkType workType);

    /**
     * Method to insert {@link WorkType}
     *
     * @param workType this {@code workType} will be inserted
     * @return returns {@code true} if {@link WorkType} inserted success
     *         or else {@code false}
     */
    boolean insertWorkType(WorkType workType);
}
