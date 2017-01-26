package org.palax.dao.transaction;

import org.palax.entity.Brigade;

import java.sql.Connection;
import java.util.List;

/**
 * The {@code BrigadeDao} interface for ORM database entity {@link Brigade} with transaction support
 *
 * @author Taras Palashynskyy
 */

public interface TransactionBrigadeDao {

    /**
     * Method to get all {@link Brigade}
     *
     * @param con {@code con} this connection that enables transactional
     * @return return {@link List} of all {@link Brigade}
     */
    List<Brigade> getAllBrigadeTransaction(Connection con);

    /**
     * Method returns {@link List} of {@link Brigade} which find by {@code  workType}
     *
     * @param con {@code con} this connection that enables transactional
     * @param workType it indicates an {@code  workType} that you want return
     * @return return {@link Brigade} by {@code workType}
     */
    List<Brigade> getAllBrigadeByWorkTypeTransaction(String workType, Connection con);

    /**
     * Method return {@link Brigade} which find by {@code id}
     *
     * @param con {@code con} this connection that enables transactional
     * @param id it indicates an {@link Brigade} {@code id} that you want return
     * @return return {@link Brigade} by {@code id}
     */
    Brigade getBrigadeByIdTransaction(Long id, Connection con);

    /**
     * Method delete {@link Brigade}
     *
     * @param brigade {@code brigade} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean deleteBrigadeTransaction(Brigade brigade, Connection con);

    /**
     * Method update {@link Brigade}
     *
     * @param con {@code con} this connection that enables transactional
     * @param brigade the {@code brigade} will update if it already exists
     * @return returns {@code true} if the {@code brigade} updated
     *         or else {@code false}
     */
    boolean updateBrigadeTransaction(Brigade brigade, Connection con);

    /**
     * Method to insert {@link Brigade}
     *
     * @param con {@code con} this connection that enables transactional
     * @param brigade this {@code brigade} will be inserted
     * @return returns {@code true} if {@link Brigade} inserted success
     *         or else {@code false}
     */
    boolean insertBrigadeTransaction(Brigade brigade, Connection con);
}
