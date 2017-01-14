package org.palax.dao.factory;

import org.palax.dao.*;
import org.palax.dao.mysql.*;

/**
 * The {@code MySQLDAOFactory} class is a factory method to
 * return instances of DAO implementations for MySQL
 *
 * @author Taras Palashynskyy
 */

public class MySQLDAOFactory {

    private MySQLDAOFactory() {

    }

    /**
     * Returns {@link MySQLBidDao} instance
     *
     * @return return {@link MySQLBidDao} instance
     */
    public static BidDao getBidDao() {
        return MySQLBidDao.getInstance();
    }

    /**
     * Returns {@link MySQLBrigadeDao} instance
     *
     * @return return {@link MySQLBrigadeDao} instance
     */
    public static BrigadeDao getBrigadeDao() {
        return MySQLBrigadeDao.getInstance();
    }

    /**
     * Returns {@link MySQLRoleDao} instance
     *
     * @return return {@link MySQLRoleDao} instance
     */
    public static RoleDao getRoleDao() {
        return MySQLRoleDao.getInstance();
    }

    /**
     * Returns {@link MySQLUserDao} instance
     *
     * @return return {@link MySQLUserDao} instance
     */
    public static UserDao getUserDao() {
        return MySQLUserDao.getInstance();
    }

    /**
     * Returns {@link MySQLWorkPlaneDao} instance
     *
     * @return return {@link MySQLWorkPlaneDao} instance
     */
    public static WorkPlaneDao getWorkPlaneDao() {
        return MySQLWorkPlaneDao.getInstance();
    }

    /**
     * Returns {@link MySQLWorkTypeDao} instance
     *
     * @return return {@link MySQLWorkTypeDao} instance
     */
    public static WorkTypeDao getWorkTypeDao() {
        return MySQLWorkTypeDao.getInstance();
    }
}
