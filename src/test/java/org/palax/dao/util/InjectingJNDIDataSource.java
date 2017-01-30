package org.palax.dao.util;

import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The {@code InjectingJNDIDataSource} is a class which used to create and inject {@link InitialContext}
 * and {@link DataSource} to the test cases without server container
 *
 * @author Taras Palashynskyy
 */
public class InjectingJNDIDataSource {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(InjectingJNDIDataSource.class);

    private static DataSource ds;

    static {
        setJNDIContextAndDataSource();
        try {
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/housing-service-test");
        } catch(NamingException e){
            logger.error("Threw a NamingException, full stack trace follows:", e);
        }
    }

    /**
     * Method initialize {@code InitialContext} and {@code DataSource} with next properties
     * <code> Url("jdbc:mysql://localhost:3306/housing_service_test"), Username("root")
     * Password("root"), dDriverClassName("com.mysql.jdbc.Driver"), MaxActive(100)
     * MaxIdle(30),MaxWait(10000)</>
     * {@link DataSource} object will be bind to {@link InitialContext} with
     * fallowing name {@code "java:/comp/env/jdbc/housing-service-test"}
     */
    private static void setJNDIContextAndDataSource() {
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");

            DataSource ds = new DataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/?verifyServerCertificate=false&useSSL=false");
            ds.setUsername("root");
            ds.setPassword("root");
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setMaxActive(100);
            ds.setMaxIdle(30);
            ds.setMaxWait(1000);

            ic.bind("java:/comp/env/jdbc/housing-service-test", ds);
        } catch(Exception e) {
            logger.error("Threw a Exception, full stack trace follows:", e);
        }
    }

    /**
     * Method should be call after initialize {@link InitialContext} and {@link DataSource}
     *
     * @return returns {@link DataSource} object which get by name {@code "jdbc/housing-service-test"}
     * from {@link InitialContext}
     */
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }
        return null;
    }

}
