package org.palax.dao;

import org.palax.entity.Brigade;

import java.util.List;

/**
 * The {@code BrigadeDao} interface for ORM database entity {@link Brigade}
 *
 * @author Taras Palashynskyy
 */

public interface BrigadeDao {

    /**
     * Method to get all {@link Brigade}
     *
     * @return return {@link List} of all {@link Brigade}
     */
    List<Brigade> getAllBrigade();

    /**
     * Method returns {@link List} of {@link Brigade} which find by {@code  workType}
     *
     * @param workType it indicates an {@code  workType} that you want return
     * @return return {@link Brigade} by {@code workType}
     */
    List<Brigade> getAllBrigadeByWorkType(String workType);

    /**
     * Method return {@link Brigade} which find by {@code id}
     *
     * @param id it indicates an {@link Brigade} {@code id} that you want return
     * @return return {@link Brigade} by {@code id}
     */
    Brigade getBrigadeById(Long id);

    /**
     * Method delete {@link Brigade}
     *
     * @param brigade {@code brigade} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteBrigade(Brigade brigade);

    /**
     * Method update {@link Brigade}
     *
     * @param brigade the {@code brigade} will update if it already exists
     * @return returns {@code true} if the {@code brigade} updated
     *         or else {@code false}
     */
    boolean updateBrigade(Brigade brigade);

    /**
     * Method to insert {@link Brigade}
     *
     * @param brigade this {@code brigade} will be inserted
     * @return returns {@code true} if {@link Brigade} inserted success
     *         or else {@code false}
     */
    boolean insertBrigade(Brigade brigade);
}
