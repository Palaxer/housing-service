package org.palax.dao.factory;

import org.palax.dao.mysql.*;
import org.palax.dao.transaction.*;

/**
 * The {@code TransactionMySQLDAOFactory} class is a factory method to
 * return instances of DAO implementations for MySQL
 *
 * @author Taras Palashynskyy
 */

public class TransactionMySQLDAOFactory {

    private TransactionMySQLDAOFactory() {

    }

    /**
     * Returns {@link MySQLBidDao} instance
     *
     * @return return {@link MySQLBidDao} instance
     */
    public static TransactionBidDao getBidDao() {
        return MySQLBidDao.getInstance();
    }

    /**
     * Returns {@link MySQLBrigadeDao} instance
     *
     * @return return {@link MySQLBrigadeDao} instance
     */
    public static TransactionBrigadeDao getBrigadeDao() {
        return MySQLBrigadeDao.getInstance();
    }

    /**
     * Returns {@link MySQLRoleDao} instance
     *
     * @return return {@link MySQLRoleDao} instance
     */
    public static TransactionRoleDao getRoleDao() {
        return MySQLRoleDao.getInstance();
    }

    /**
     * Returns {@link MySQLUserDao} instance
     *
     * @return return {@link MySQLUserDao} instance
     */
    public static TransactionUserDao getUserDao() {
        return MySQLUserDao.getInstance();
    }

    /**
     * Returns {@link MySQLWorkPlaneDao} instance
     *
     * @return return {@link MySQLWorkPlaneDao} instance
     */
    public static TransactionWorkPlaneDao getWorkPlaneDao() {
        return MySQLWorkPlaneDao.getInstance();
    }

    /**
     * Returns {@link MySQLWorkTypeDao} instance
     *
     * @return return {@link MySQLWorkTypeDao} instance
     */
    public static TransactionWorkTypeDao getWorkTypeDao() {
        return MySQLWorkTypeDao.getInstance();
    }
}
