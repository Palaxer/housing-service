package org.palax.dao.transaction;

import org.palax.entity.WorkType;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code WorkTypeDao} interface for ORM database entity {@link WorkType} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionWorkTypeDao {
    /**
     * Method to get all {@link WorkType}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link WorkType}
     */
    List<WorkType> getAllWorkTypeTransaction(Connection con);

    /**
     *  Method return {@link WorkType} which find by {@code name}
     *
     * @param con {@code con} this connection that enables transactional
     * @param name specifies the {@code name} of the {@link WorkType} you want to search
     * @return return {@link WorkType} which find by {@code name}
     */
    WorkType getWorkTypeByNameTransaction(String name, Connection con);

    /**
     * Method return {@link WorkType} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id specifies the {@code id} of the {@link WorkType} you want to search
     * @return return {@link WorkType} which find by {@code name}
     */
    WorkType getWorkTypeByIdTransaction(Long id, Connection con);

    /**
     * Method delete {@link WorkType}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workType {@code workType} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteWorkTypeTransaction(WorkType workType,Connection con);

    /**
     * Method update {@link WorkType}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workType the {@code workType} will update if it already exists
     * @return returns {@code true} if the {@code workType} updated
     *         or else {@code false}
     */
    boolean updateWorkTypeTransaction(WorkType workType, Connection con);

    /**
     * Method to insert {@link WorkType}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workType this {@code workType} will be inserted
     * @return returns {@code true} if {@link WorkType} inserted success
     *         or else {@code false}
     */
    boolean insertWorkTypeTransaction(WorkType workType, Connection con);
}
