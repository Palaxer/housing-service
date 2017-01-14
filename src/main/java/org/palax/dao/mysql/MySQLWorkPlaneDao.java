package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.WorkPlaneDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Bid;
import org.palax.entity.Brigade;
import org.palax.entity.User;
import org.palax.entity.WorkPlane;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLWorkPlaneDao} singleton class implements {@link WorkPlaneDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLWorkPlaneDao implements WorkPlaneDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLWorkPlaneDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLWorkPlaneDao mySQLWorkPlaneDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int WORK_PLANE_ID = 1;
    private static final int USER_ADVISOR_ID = 2;
    private static final int BRIGADE_ID = 3;
    private static final int BID_ID = 4;
    private static final int STATUS = 5;
    private static final int WORK_TIME = 6;
    private static final int COMPLETE_TIME = 7;

    private MySQLWorkPlaneDao() {

    }

    /**
     * Always return same {@link MySQLWorkPlaneDao} instance
     *
     * @return always return same {@link MySQLWorkPlaneDao} instance
     */
    public synchronized static MySQLWorkPlaneDao getInstance() {
        if(mySQLWorkPlaneDao == null) {
            mySQLWorkPlaneDao = new MySQLWorkPlaneDao();
            logger.debug("Create first MySQLWorkPlaneDao instance");
        }
        logger.debug("Return MySQLWorkPlaneDao instance");
        return mySQLWorkPlaneDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkPlane> getAllWorkPlane() {
        String SQL = "SELECT * FROM housing_service.work_plane";

        List<WorkPlane> workPlaneList = null;

        logger.debug("Try get all WORK_PLANE");

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            workPlaneList = parseResultSet(rs);

            logger.debug("Get all WORK_PLANE successfully " + workPlaneList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, rs);
        }

        return workPlaneList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkPlane> getAllWorkPlaneByWorkType(String workType) {
        String SQL = "SELECT A.WORK_PLANE_ID, A.USER_ADVISOR_ID, A.BRIGADE_ID, A.BID_ID, A.STATUS, A.WORK_TIME, A.COMPLETE_TIME " +
                "FROM housing_service.work_plane A " +
                "LEFT JOIN housing_service.brigade B ON (A.BRIGADE_ID=B.BRIGADE_ID) " +
                "LEFT JOIN housing_service.work_type C ON (B.WORK_TYPE_ID=C.WORK_TYPE_ID) " +
                "WHERE C.TYPE_NAME=?";

        List<WorkPlane> workPlaneList = null;

        logger.debug("Try get all WORK_PLANE by WORK_TYPE");

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, workType);
            rs = stm.executeQuery();

            workPlaneList = parseResultSet(rs);

            logger.debug("Get all WORK_PLANE by WORK_TYPE successfully " + workPlaneList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, rs);
        }

        return workPlaneList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkPlane> getAllWorkPlaneByBrigadeId(Long id) {
        String SQL = "SELECT A.WORK_PLANE_ID, A.USER_ADVISOR_ID, A.BRIGADE_ID, A.BID_ID, A.STATUS, A.WORK_TIME, A.COMPLETE_TIME " +
                "FROM housing_service.work_plane A " +
                "WHERE A.BRIGADE_ID=?";

        List<WorkPlane> workPlaneList = null;

        logger.debug("Try get all WORK_PLANE by WORK_TYPE");

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            workPlaneList = parseResultSet(rs);

            logger.debug("Get all WORK_PLANE by WORK_TYPE successfully " + workPlaneList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, rs);
        }

        return workPlaneList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkPlane getWorkPlaneById(Long id) {
        String SQL = "SELECT * FROM housing_service.work_plane WHERE WORK_PLANE_ID=?";

        WorkPlane workPlane = null;

        logger.debug("Try get WORK_PLANE by ID " + id);

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            workPlane = parseResultSet(rs).get(0);

            logger.debug("Get WORK_PLANE by ID successfully " + workPlane);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, rs);
        }

        return workPlane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkPlane getWorkPlaneByBidId(Long id) {
        String SQL = "SELECT * FROM housing_service.work_plane WHERE BID_ID=?";

        WorkPlane workPlane = null;

        logger.debug("Try get WORK_PLANE by BID_ID " + id);

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            workPlane = parseResultSet(rs).get(0);

            logger.debug("Get WORK_PLANE by BID_ID successfully " + workPlane);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, rs);
        }

        return workPlane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteWorkPlane(WorkPlane workPlane) {
        String SQL = "DELETE FROM housing_service.work_plane WHERE WORK_PLANE_ID=?";

        logger.debug("Try delete WORK_PLANE " + workPlane);

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, workPlane.getWorkPlaneId());
            if(stm.executeUpdate() > 0) {
                logger.debug("WORK_PLANE delete successfully " + workPlane);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, null);
        }
        logger.debug("WORK_PLANE delete fail " + workPlane);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateWorkPlane(WorkPlane workPlane) {
        String SQL = "UPDATE housing_service.work_plane SET BRIGADE_ID=?, BID_ID=?, STATUS=?, USER_ADVISOR_ID=?," +
                " WORK_TIME=?, COMPLETE_TIME=? WHERE WORK_PLANE_ID=?";

        logger.debug("Try update WORK_PLANE " + workPlane);

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            Long brigadeId = workPlane.getBrigade() != null ? workPlane.getBrigade().getBrigadeId() : null;
            if(brigadeId == null)
                stm.setNull(1,Types.INTEGER);
            else
                stm.setLong(1, brigadeId);
            Long bidId = workPlane.getBid() != null ? workPlane.getBid().getBidId() : null;
            if(bidId == null)
                stm.setNull(2,Types.INTEGER);
            else
                stm.setLong(2, bidId);
            stm.setString(3, workPlane.getStatus());
            Long userId = workPlane.getUserAdvisor() != null ? workPlane.getUserAdvisor().getUserId() : null;
            if(userId == null)
                stm.setNull(4,Types.INTEGER);
            else
                stm.setLong(4, userId);
            stm.setTimestamp(5, workPlane.getWorkTime());
            stm.setTimestamp(6, workPlane.getCompleteTime());
            stm.setLong(7, workPlane.getWorkPlaneId());
            if(stm.executeUpdate() > 0) {
                logger.debug("WORK_PLANE update successfully " + workPlane);
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try update new WORK_PLANE " + workPlane);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, null);
        }
        logger.debug("WORK_PLANE update fail " + workPlane);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertWorkPlane(WorkPlane workPlane) {
        String SQL = "INSERT INTO housing_service.work_plane (BRIGADE_ID, BID_ID, STATUS, USER_ADVISOR_ID, WORK_TIME," +
                " COMPLETE_TIME) VALUES (?, ?, ?, ?, ?, ?)";

        logger.debug("Try insert WORK_PLANE " + workPlane);

        Connection con = DataSourceManager.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            Long brigadeId = workPlane.getBrigade() != null ? workPlane.getBrigade().getBrigadeId() : null;
            if(brigadeId == null)
                stm.setNull(1,Types.INTEGER);
            else
                stm.setLong(1, brigadeId);
            Long bidId = workPlane.getBid() != null ? workPlane.getBid().getBidId() : null;
            if(bidId == null)
                stm.setNull(2,Types.INTEGER);
            else
                stm.setLong(2, bidId);
            stm.setString(3, workPlane.getStatus());
            Long userId = workPlane.getUserAdvisor() != null ? workPlane.getUserAdvisor().getUserId() : null;
            if(userId == null)
                stm.setNull(4,Types.INTEGER);
            else
                stm.setLong(4, userId);
            stm.setTimestamp(5, workPlane.getWorkTime());
            stm.setTimestamp(6, workPlane.getCompleteTime());
            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    workPlane.setWorkPlaneId(rs.getLong(1));
                    logger.debug("WORK_PLANE insert successfully " + workPlane);
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try create new WORK_PLANE " + workPlane);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, stm, null);
        }
        logger.debug("WORK_PLANE insert fail " + workPlane);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link List} of {@link WorkPlane}
     *
     * @param rs {@link ResultSet} which be persed
     * @return parse {@code rs} and return {@link List} of {@link WorkPlane}
     * @throws SQLException {@link SQLException}
     */
    private List<WorkPlane> parseResultSet(ResultSet rs) throws SQLException {
        List<WorkPlane> workPlaneList = new ArrayList<>();
        WorkPlane workPlane;
        while(rs.next()) {
            workPlane = new WorkPlane();

            workPlane.setWorkPlaneId(rs.getLong(WORK_PLANE_ID));
            User user = new User();
            if(rs.getLong(USER_ADVISOR_ID) != 0)
                user = MySQLDAOFactory.getUserDao().getUserById(rs.getLong(USER_ADVISOR_ID));
            workPlane.setUserAdvisor(user);
            Brigade brigade = new Brigade();
            if(rs.getLong(BRIGADE_ID) != 0)
                brigade = MySQLDAOFactory.getBrigadeDao().getBrigadeById(rs.getLong(BRIGADE_ID));
            workPlane.setBrigade(brigade);
            Bid bid = new Bid();
            if(rs.getLong(BID_ID) != 0)
                bid = MySQLDAOFactory.getBidDao().getBidById(rs.getLong(BID_ID));
            workPlane.setBid(bid);
            workPlane.setStatus(rs.getString(STATUS));
            workPlane.setWorkTime(rs.getTimestamp(WORK_TIME));
            workPlane.setCompleteTime(rs.getTimestamp(COMPLETE_TIME));

            workPlaneList.add(workPlane);
        }

        return workPlaneList;
    }
}
