package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.WorkTypeDao;
import org.palax.dao.transaction.TransactionWorkTypeDao;
import org.palax.entity.WorkType;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLWorkTypeDao} singleton class implements {@link WorkTypeDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLWorkTypeDao implements WorkTypeDao, TransactionWorkTypeDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLWorkTypeDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLWorkTypeDao mySQLWorkTypeDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int WORK_TYPE_ID = 1;
    private static final int TYPE_NAME = 2;

    private MySQLWorkTypeDao() {

    }

    /**
     * Always return same {@link MySQLWorkTypeDao} instance
     *
     * @return always return same {@link MySQLWorkTypeDao} instance
     */
    public synchronized static MySQLWorkTypeDao getInstance(){
        if(mySQLWorkTypeDao == null) {
            mySQLWorkTypeDao = new MySQLWorkTypeDao();
            logger.debug("Create first MySQLWorkTypeDao instance");
        }
        logger.debug("Return MySQLWorkTypeDao instance");
        return mySQLWorkTypeDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkType> getAllWorkType() {
        Connection con = DataSourceManager.getConnection();
        List<WorkType> workTypeList = getAllWorkTypeTransaction(con);
        DataSourceManager.closeAll(con, null, null);

        return workTypeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkType> getAllWorkTypeTransaction(Connection con) {
        String SQL = "SELECT * FROM work_type";

        ArrayList<WorkType> workTypeList = new ArrayList<>();
        WorkType workType;

        logger.debug("Try get all WORK_TYPE");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            while(rs.next()){
                workType = new WorkType();

                workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID));
                workType.setTypeName(rs.getString(TYPE_NAME));

                workTypeList.add(workType);
            }
            logger.debug("Get all WORK_TYPE successfully " + workTypeList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return workTypeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkType getWorkTypeByName(String name) {
        Connection con = DataSourceManager.getConnection();
        WorkType workType = getWorkTypeByNameTransaction(name, con);
        DataSourceManager.closeAll(con, null, null);

        return workType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkType getWorkTypeByNameTransaction(String name, Connection con) {
        String SQL = "SELECT * FROM work_type WHERE TYPE_NAME=?";

        WorkType workType = null;

        logger.debug("Try get WORK_TYPE by NAME " + name);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, name);
            rs = stm.executeQuery();

            while(rs.next()){
                workType = new WorkType();

                workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID));
                workType.setTypeName(rs.getString(TYPE_NAME));
            }
            logger.debug("Get WORK_TYPE by NAME successfully " + workType);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return workType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkType getWorkTypeById(Long id) {
        Connection con = DataSourceManager.getConnection();
        WorkType workType = getWorkTypeByIdTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return workType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkType getWorkTypeByIdTransaction(Long id, Connection con) {
        String SQL = "SELECT * FROM work_type WHERE WORK_TYPE_ID=?";

        WorkType workType = null;

        logger.debug("Try get WORK_TYPE by ID " + id);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            while(rs.next()){
                workType = new WorkType();

                workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID));
                workType.setTypeName(rs.getString(TYPE_NAME));
            }
            logger.debug("Get WORK_TYPE by ID successfully " + workType);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return workType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteWorkType(WorkType workType) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = deleteWorkTypeTransaction(workType, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteWorkTypeTransaction(WorkType workType, Connection con) {
        String SQL = "DELETE FROM work_type WHERE WORK_TYPE_ID=?";

        logger.debug("Try delete WORK_TYPE " + workType);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, workType.getWorkTypeId());
            if(stm.executeUpdate() > 0) {
                logger.debug("WORK_TYPE delete successfully " + workType);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("WORK_TYPE delete fail " + workType);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateWorkType(WorkType workType) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = updateWorkTypeTransaction(workType, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateWorkTypeTransaction(WorkType workType, Connection con) {
        String SQL = "UPDATE work_type SET TYPE_NAME=? WHERE WORK_TYPE_ID=?";

        logger.debug("Try update WORK_TYPE " + workType);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, workType.getTypeName());
            stm.setLong(2, workType.getWorkTypeId());
            if(stm.executeUpdate() > 0) {
                logger.debug("WORK_TYPE update successfully " + workType);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("WORK_TYPE update fail " + workType);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertWorkType(WorkType workType) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = insertWorkTypeTransaction(workType, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertWorkTypeTransaction(WorkType workType, Connection con) {
        String SQL = "INSERT INTO work_type (TYPE_NAME)  " +
                "VALUES (?)";

        logger.debug("Try insert WORK_TYPE " + workType);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, workType.getTypeName());
            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    workType.setWorkTypeId(rs.getLong(1));
                    logger.debug("WORK_TYPE insert successfully " + workType);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("WORK_TYPE insert fail " + workType);
        return false;
    }
}
