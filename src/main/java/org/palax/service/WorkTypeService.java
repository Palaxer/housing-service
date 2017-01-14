package org.palax.service;

import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.WorkType;

import java.util.List;

/**
 * The {@code WorkTypeService} service is a convenient API for working with the {@link org.palax.dao.WorkTypeDao}
 *
 * @author Taras Palashynskyy
 */

public class WorkTypeService {

    private WorkTypeService() {

    }

    /**
     * Method to get all {@link WorkType}
     *
     * @return return {@link List} of all {@link WorkType}
     */
    public static List<WorkType> getAllWorkType() {
        return MySQLDAOFactory.getWorkTypeDao().getAllWorkType();
    }

    /**
     *  Method return {@link WorkType} which find by {@code workTypeName}
     *
     * @param workTypeName specifies the {@code workTypeName} of the {@link WorkType} you want to search
     * @return return {@link WorkType} which find by {@code workTypeName}
     */
    public static WorkType getWorkTypeByName(String workTypeName) {
        return MySQLDAOFactory.getWorkTypeDao().getWorkTypeByName(workTypeName);
    }

    /**
     * Method to add {@link WorkType}
     *
     * @param workType this {@code workType} will be added
     * @return returns {@code true} if {@link WorkType} add success
     *         or else {@code false}
     */
    public static boolean addWorkType(WorkType workType) {
        return MySQLDAOFactory.getWorkTypeDao().insertWorkType(workType);
    }

    /**
     * Method update {@link WorkType}
     *
     * @param workType the {@code workType} will update if it already exists
     * @return returns {@code true} if the {@code workType} updated
     *         or else {@code false}
     */
    public static boolean updateWorkType(WorkType workType) {
        return MySQLDAOFactory.getWorkTypeDao().updateWorkType(workType);
    }

}
