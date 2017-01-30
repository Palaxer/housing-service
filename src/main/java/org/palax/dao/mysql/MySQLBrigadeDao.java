package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.BrigadeDao;
import org.palax.dao.transaction.TransactionBrigadeDao;
import org.palax.entity.Brigade;
import org.palax.entity.WorkType;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLBrigadeDao} singleton class implements {@link BrigadeDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLBrigadeDao implements BrigadeDao, TransactionBrigadeDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLBrigadeDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLBrigadeDao mySQLBrigadeDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int BRIGADE_ID = 1;
    private static final int BRIGADE_NAME = 2;
    private static final int WORK_TYPE_ID = 3;
    private static final int TYPE_NAME = 4;

    private MySQLBrigadeDao() {

    }

    /**
     * Always return same {@link MySQLBrigadeDao} instance
     *
     * @return always return same {@link MySQLBrigadeDao} instance
     */
    public synchronized static MySQLBrigadeDao getInstance() {
        if(mySQLBrigadeDao == null) {
            mySQLBrigadeDao = new MySQLBrigadeDao();
            logger.debug("Create first MySQLBrigadeDao instance");
        }
        logger.debug("Return MySQLBrigadeDao instance");
        return mySQLBrigadeDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brigade> getAllBrigade() {
        Connection con = DataSourceManager.getConnection();
        List<Brigade> brigadeList = getAllBrigadeTransaction(con);
        DataSourceManager.closeAll(con, null, null);

        return  brigadeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brigade> getAllBrigadeTransaction(Connection con){
        String SQL = "SELECT A.BRIGADE_ID, A.BRIGADE_NAME, A.WORK_TYPE_ID, B.TYPE_NAME " +
                "FROM brigade A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID)";

        List<Brigade> brigadeList = null;

        logger.debug("Try get all BRIGADE");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            brigadeList = parseResultSet(rs);

            logger.debug("Get all BRIGADE successfully " + brigadeList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return brigadeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brigade> getAllBrigadeByWorkType(String workType) {
        Connection con = DataSourceManager.getConnection();
        List<Brigade> brigadeList = getAllBrigadeByWorkTypeTransaction(workType, con);
        DataSourceManager.closeAll(con, null, null);

        return  brigadeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Brigade> getAllBrigadeByWorkTypeTransaction(String workType, Connection con) {
        String SQL = "SELECT A.BRIGADE_ID, A.BRIGADE_NAME, A.WORK_TYPE_ID, B.TYPE_NAME " +
                "FROM brigade A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "WHERE B.TYPE_NAME=?";

        List<Brigade> brigadeList = null;

        logger.debug("Try get BRIGADE by WORK_TYPE " + workType);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, workType);
            rs = stm.executeQuery();

            brigadeList = parseResultSet(rs);

            logger.debug("Get BRIGADE by WORK_TYPE successfully " + brigadeList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return brigadeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brigade getBrigadeById(Long id) {
        Connection con = DataSourceManager.getConnection();
        Brigade brigade = getBrigadeByIdTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return  brigade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Brigade getBrigadeByIdTransaction(Long id, Connection con) {
        String SQL = "SELECT A.BRIGADE_ID, A.BRIGADE_NAME, A.WORK_TYPE_ID, B.TYPE_NAME\n" +
                "FROM brigade A\n" +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "WHERE A.BRIGADE_ID=?";

        Brigade brigade = null;

        logger.debug("Try get BRIGADE by ID " + id);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            List<Brigade> brigadeList = parseResultSet(rs);
            brigade = brigadeList.isEmpty() ? null : brigadeList.get(0);

            logger.debug("Get BRIGADE by ID successfully " + brigade);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return brigade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBrigade(Brigade brigade) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = deleteBrigadeTransaction(brigade, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBrigadeTransaction(Brigade brigade, Connection con) {
        String SQL = "DELETE FROM brigade WHERE BRIGADE_ID=?";

        logger.debug("Try delete BRIGADE " + brigade);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, brigade.getBrigadeId());
            if(stm.executeUpdate() > 0) {
                logger.debug("BRIGADE delete successfully " + brigade);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("BRIGADE delete fail " + brigade);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateBrigade(Brigade brigade) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = updateBrigadeTransaction(brigade, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateBrigadeTransaction(Brigade brigade, Connection con) {
        String SQL = "UPDATE brigade SET BRIGADE_NAME=?, WORK_TYPE_ID=? WHERE BRIGADE_ID=?";

        logger.debug("Try update BRIGADE " + brigade);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, brigade.getBrigadeName());
            Long workTypeId = brigade.getWorkType() != null ? brigade.getWorkType().getWorkTypeId() : null;
            if(workTypeId == null)
                stm.setNull(2,Types.INTEGER);
            else
                stm.setLong(2, workTypeId);
            stm.setLong(3, brigade.getBrigadeId());
            if(stm.executeUpdate() > 0) {
                logger.debug("BRIGADE update successfully " + brigade);
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try update new BRIGADE " + brigade);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("BRIGADE update fail " + brigade);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertBrigade(Brigade brigade) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = insertBrigadeTransaction(brigade, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertBrigadeTransaction(Brigade brigade, Connection con) {
        String SQL = "INSERT INTO brigade (BRIGADE_NAME, WORK_TYPE_ID)  " +
                "VALUES (?, ?)";

        logger.debug("Try insert BRIGADE " + brigade);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, brigade.getBrigadeName());
            Long workTypeId = brigade.getWorkType() != null ? brigade.getWorkType().getWorkTypeId() : null;
            if(workTypeId == null)
                stm.setNull(2,Types.INTEGER);
            else
                stm.setLong(2, workTypeId);
            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    brigade.setBrigadeId(rs.getLong(1));
                    logger.debug("BRIGADE insert successfuly " + brigade);
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try create new BRIGADE " + brigade);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("BRIGADE insert fail " + brigade);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link List} of {@link Brigade}
     *
     * @param rs {@link ResultSet} which be persed
     * @return parse {@code rs} and return {@link List} of {@link Brigade}
     */
    private List<Brigade> parseResultSet(ResultSet rs) throws SQLException {
        List<Brigade> brigadeList = new ArrayList<>();
        Brigade brigade;
        while(rs.next()) {
            brigade = new Brigade();
            brigade.setBrigadeId(rs.getLong(BRIGADE_ID) == 0 ? null : rs.getLong(BRIGADE_ID));
            brigade.setBrigadeName(rs.getString(BRIGADE_NAME));
            WorkType workType = new WorkType();
            workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID) == 0 ? null : rs.getLong(WORK_TYPE_ID));
            workType.setTypeName(rs.getString(TYPE_NAME));
            brigade.setWorkType(workType);

            brigadeList.add(brigade);
        }

        return brigadeList;
    }
}
