package org.palax.utils;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The {@code DataSourceManager} class and used to set
 * {@code UTF-8} character encoding for all requests
 *
 * @author Taras Palashynskyy
 */

public class DataSourceManager {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DataSourceManager.class);
    /**Data source object which use to get db connection. */
    private static DataSource ds;

    /**
     * Initialization data source object if the @{code dataSource} is not initialized
     * is stored @{code null} value
     */
    static {
        try {
            logger.debug("Try to get connection pool");
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/housing-service");
            if (ds != null)
                logger.debug("Connection pool is successfully received");
            else
                logger.error("Connection pool is not received");
        } catch (NamingException e) {
            logger.error("Threw a NamingException, full stack trace follows:",e);
        }
    }

    private DataSourceManager() {

    }

    /**
     * Method use to get {@link Connection} from {@code dataSource} connection pool
     *
     * @return {@link Connection} which comes from connection pool
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            logger.debug("Try get connection");
            connection = ds.getConnection();
            if (connection != null)
                logger.debug("Connection is successfully received");
            else
                logger.error("Connection is not received");
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return connection;
    }

    /**
     * Method which used to close {@link Connection}, {@link Statement} and {@link ResultSet}
     *
     * @param con {@link Connection} to be closed
     * @param stm {@link Statement} to be closed
     * @param rs {@link ResultSet} to be closed
     */
    public static void closeAll(Connection con, Statement stm, ResultSet rs) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            try {
                if (stm != null)
                    stm.close();
            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:",e);
            } finally {
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException e) {
                    logger.error("Threw a SQLException, full stack trace follows:",e);
                }

            }
        }
    }
}
