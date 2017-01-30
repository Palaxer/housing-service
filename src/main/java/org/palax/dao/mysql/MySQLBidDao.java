package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.BidDao;
import org.palax.dao.transaction.TransactionBidDao;
import org.palax.entity.*;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLBidDao} singleton class implements {@link BidDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLBidDao implements BidDao, TransactionBidDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLBidDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLBidDao mySQLBidDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int BID_ID = 1;
    private static final int WORK_TYPE_ID = 2;
    private static final int TYPE_NAME = 3;
    private static final int WORK_SCOPE = 4;
    private static final int LEAD_TIME = 5;
    private static final int USER_TENANT_ID = 6;
    private static final int LOGIN = 7;
    private static final int PASSWORD = 8;
    private static final int ROLE_ID = 9;
    private static final int ROLE_TYPE = 10;
    private static final int FIRST_NAME = 11;
    private static final int LAST_NAME = 12;
    private static final int POSITION = 13;
    private static final int BRIGADE_ID = 14;
    private static final int BRIGADE_NAME = 15;
    private static final int BRIGADE_WORK_TYPE_ID = 16;
    private static final int BRIGADE_TYPE_NAME = 17;
    private static final int STREET = 18;
    private static final int HOUSE_NUMBER = 19;
    private static final int APARTMENT = 20;
    private static final int CITY = 21;
    private static final int STATUS = 22;
    private static final int DESCRIPTION = 23;
    private static final int BID_TIME = 24;
    private static final int PHONE_NUMBER = 25;

    private MySQLBidDao(){

    }


    /**
     * Always return same {@link MySQLBidDao} instance
     *
     * @return always return same {@link MySQLBidDao} instance
     */
    public synchronized static MySQLBidDao getInstance() {
        if(mySQLBidDao == null) {
            mySQLBidDao = new MySQLBidDao();
            logger.debug("Create first MySQLBidDao instance");
        }
        logger.debug("Return MySQLBidDao instance");
        return mySQLBidDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBid() {
        Connection con = DataSourceManager.getConnection();
        List<Bid> bidList = getAllBidTransaction(con);
        DataSourceManager.closeAll(con, null, null);

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBidTransaction(Connection con) {
        String SQL = "SELECT A.BID_ID, A.WORK_TYPE_ID, B.TYPE_NAME, A.WORK_SCOPE, A.LEAD_TIME, " +
                "A.USER_TENANT_ID, C.LOGIN, C.PASSWD, C.ROLE_ID, D.ROLE_TYPE, C.FIRST_NAME," +
                "C.LAST_NAME, C.POSITION, C.BRIGADE_ID, E.BRIGADE_NAME, E.WORK_TYPE_ID, " +
                "F.TYPE_NAME, C.STREET, C.HOUSE_NUMBER, C.APARTMENT, C.CITY, A.STATUS, A.DESCRIPTION, A.BID_TIME, C.PHONE_NUMBER " +
                "FROM bid A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "LEFT JOIN user C ON (A.USER_TENANT_ID=C.USER_ID) " +
                "LEFT JOIN role D ON (C.ROLE_ID=D.ROLE_ID) " +
                "LEFT JOIN brigade E ON (C.BRIGADE_ID=E.BRIGADE_ID) " +
                "LEFT JOIN work_type F ON (E.WORK_TYPE_ID=F.WORK_TYPE_ID)";

        List<Bid> bidList = null;

        logger.debug("Try get all BID");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            bidList = parseResultSet(rs);

            logger.debug("Get all BID successfully " + bidList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBidByStatus(String status) {
        Connection con = DataSourceManager.getConnection();
        List<Bid> bidList = getAllBidByStatusTransaction(status, con);
        DataSourceManager.closeAll(con, null, null);

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBidByStatusTransaction(String status, Connection con) {
        String SQL = "SELECT A.BID_ID, A.WORK_TYPE_ID, B.TYPE_NAME, A.WORK_SCOPE, A.LEAD_TIME, " +
                "A.USER_TENANT_ID, C.LOGIN, C.PASSWD, C.ROLE_ID, D.ROLE_TYPE, C.FIRST_NAME," +
                "C.LAST_NAME, C.POSITION, C.BRIGADE_ID, E.BRIGADE_NAME, E.WORK_TYPE_ID, " +
                "F.TYPE_NAME, C.STREET, C.HOUSE_NUMBER, C.APARTMENT, C.CITY, A.STATUS, A.DESCRIPTION, A.BID_TIME, C.PHONE_NUMBER " +
                "FROM bid A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "LEFT JOIN user C ON (A.USER_TENANT_ID=C.USER_ID) " +
                "LEFT JOIN role D ON (C.ROLE_ID=D.ROLE_ID) " +
                "LEFT JOIN brigade E ON (C.BRIGADE_ID=E.BRIGADE_ID) " +
                "LEFT JOIN work_type F ON (E.WORK_TYPE_ID=F.WORK_TYPE_ID)" +
                "WHERE A.STATUS=?";

        List<Bid> bidList = new ArrayList<>();

        logger.debug("Try get all BID by STATUS");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, status);
            rs = stm.executeQuery();

            bidList = parseResultSet(rs);

            logger.debug("Get all BID by STATUS successfully " + bidList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBidByUserTenant(Long id) {
        Connection con = DataSourceManager.getConnection();
        List<Bid> bidList = getAllBidByUserTenantTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bid> getAllBidByUserTenantTransaction(Long id, Connection con) {
        String SQL = "SELECT A.BID_ID, A.WORK_TYPE_ID, B.TYPE_NAME, A.WORK_SCOPE, A.LEAD_TIME, " +
                "A.USER_TENANT_ID, C.LOGIN, C.PASSWD, C.ROLE_ID, D.ROLE_TYPE, C.FIRST_NAME," +
                "C.LAST_NAME, C.POSITION, C.BRIGADE_ID, E.BRIGADE_NAME, E.WORK_TYPE_ID, " +
                "F.TYPE_NAME, C.STREET, C.HOUSE_NUMBER, C.APARTMENT, C.CITY, A.STATUS, A.DESCRIPTION, A.BID_TIME, C.PHONE_NUMBER " +
                "FROM bid A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "LEFT JOIN user C ON (A.USER_TENANT_ID=C.USER_ID) " +
                "LEFT JOIN role D ON (C.ROLE_ID=D.ROLE_ID) " +
                "LEFT JOIN brigade E ON (C.BRIGADE_ID=E.BRIGADE_ID) " +
                "LEFT JOIN work_type F ON (E.WORK_TYPE_ID=F.WORK_TYPE_ID) " +
                "WHERE A.USER_TENANT_ID=?";

        List<Bid> bidList = new ArrayList<>();

        logger.debug("Try get all BID by USER_ID");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            bidList = parseResultSet(rs);

            logger.debug("Get all BID by USER_ID successfully " + bidList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return bidList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bid getBidById(Long id) {
        Connection con = DataSourceManager.getConnection();
        Bid bid = getBidByIdTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return bid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bid getBidByIdTransaction(Long id, Connection con) {
        String SQL = "SELECT A.BID_ID, A.WORK_TYPE_ID, B.TYPE_NAME, A.WORK_SCOPE, A.LEAD_TIME, " +
                "A.USER_TENANT_ID, C.LOGIN, C.PASSWD, C.ROLE_ID, D.ROLE_TYPE, C.FIRST_NAME," +
                "C.LAST_NAME, C.POSITION, C.BRIGADE_ID, E.BRIGADE_NAME, E.WORK_TYPE_ID, " +
                "F.TYPE_NAME, C.STREET, C.HOUSE_NUMBER, C.APARTMENT, C.CITY, A.STATUS, A.DESCRIPTION, A.BID_TIME, C.PHONE_NUMBER " +
                "FROM bid A " +
                "LEFT JOIN work_type B ON (A.WORK_TYPE_ID=B.WORK_TYPE_ID) " +
                "LEFT JOIN user C ON (A.USER_TENANT_ID=C.USER_ID) " +
                "LEFT JOIN role D ON (C.ROLE_ID=D.ROLE_ID) " +
                "LEFT JOIN brigade E ON (C.BRIGADE_ID=E.BRIGADE_ID) " +
                "LEFT JOIN work_type F ON (E.WORK_TYPE_ID=F.WORK_TYPE_ID) " +
                "WHERE A.BID_ID=?";

        Bid bid = null;

        logger.debug("Try get BID by ID " + id);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            List<Bid> bidList = parseResultSet(rs);
            bid = bidList.isEmpty() ? null : bidList.get(0);

            logger.debug("Get BID by ID successfully " + bid);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return bid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBid(Bid bid) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = deleteBidTransaction(bid, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBidTransaction(Bid bid, Connection con) {
        String SQL = "DELETE FROM bid WHERE BID_ID=?";

        logger.debug("Try delete BID " + bid);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, bid.getBidId());
            if(stm.executeUpdate() > 0) {
                logger.debug("BID delete successfully " + bid);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("BID delete fail " + bid);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateBid(Bid bid) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = updateBidTransaction(bid, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateBidTransaction(Bid bid, Connection con) {
        String SQL = "UPDATE bid " +
                "SET WORK_TYPE_ID=?, WORK_SCOPE=?, LEAD_TIME=?, USER_TENANT_ID=?, STATUS=?, DESCRIPTION=?, BID_TIME=? " +
                "WHERE BID_ID=?";

        logger.debug("Try update BID " + bid);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            Long workTypeId = bid.getWorkType() != null ? bid.getWorkType().getWorkTypeId() : null;
            if(workTypeId == null)
                stm.setNull(1,Types.INTEGER);
            else
                stm.setLong(1, workTypeId);
            stm.setLong(1, bid.getWorkType().getWorkTypeId());
            stm.setLong(2, bid.getWorkScope());
            stm.setTimestamp(3, bid.getLeadTime());
            Long userId = bid.getUserTenant() != null ? bid.getUserTenant().getUserId() : null;
            if(userId == null)
                stm.setNull(4,Types.INTEGER);
            else
                stm.setLong(4, userId);
            stm.setLong(4, bid.getUserTenant().getUserId());
            stm.setString(5, bid.getStatus());
            stm.setString(6, bid.getDescription());
            stm.setTimestamp(7, bid.getBidTime());
            stm.setLong(8, bid.getBidId());
            if(stm.executeUpdate() > 0) {
                logger.debug("BID update successfully " + bid);
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try update new BID " + bid);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("BID update fail " + bid);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertBid(Bid bid) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = insertBidTransaction(bid, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertBidTransaction(Bid bid, Connection con) {
        String SQL = "INSERT INTO bid (WORK_TYPE_ID, WORK_SCOPE, LEAD_TIME, USER_TENANT_ID, " +
                "STATUS, DESCRIPTION, BID_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";

        logger.debug("Try insert BID " + bid);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            Long workTypeId = bid.getWorkType() != null ? bid.getWorkType().getWorkTypeId() : null;
            if(workTypeId == null)
                stm.setNull(1,Types.INTEGER);
            else
                stm.setLong(1, workTypeId);
            stm.setLong(2, bid.getWorkScope());
            stm.setTimestamp(3, bid.getLeadTime());
            Long userId = bid.getUserTenant() != null ? bid.getUserTenant().getUserId() : null;
            if(userId == null)
                stm.setNull(4,Types.INTEGER);
            else
                stm.setLong(4, userId);
            stm.setString(5, bid.getStatus());
            stm.setString(6, bid.getDescription());
            stm.setTimestamp(7, bid.getBidTime());
            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    bid.setBidId(rs.getLong(1));
                    logger.debug("BID insert successfully " + bid);
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try create new BID " + bid);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }
        logger.debug("BID insert fail " + bid);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link List} of {@link Bid}
     *
     * @param rs {@link ResultSet} which be persed
     * @return parse {@code rs} and return {@link List} of {@link Bid}
     */
    private List<Bid> parseResultSet(ResultSet rs) throws SQLException {
        List<Bid> bidList = new ArrayList<>();
        Bid bid;
        while(rs.next()) {
            bid = new Bid();

            bid.setBidId(rs.getLong(BID_ID));
            bid.setBidTime(rs.getTimestamp(BID_TIME));
            bid.setDescription(rs.getString(DESCRIPTION));
            bid.setLeadTime(rs.getTimestamp(LEAD_TIME));
            bid.setStatus(rs.getString(STATUS));
            User user = new User();
            user.setUserId(rs.getLong(USER_TENANT_ID) == 0 ? null : rs.getLong(USER_TENANT_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setPassword(rs.getString(PASSWORD));
            Role role = new Role();
            role.setRoleId(rs.getLong(ROLE_ID) == 0 ? null : rs.getLong(ROLE_ID));
            role.setRoleType(rs.getString(ROLE_TYPE));
            user.setRole(role);
            user.setFirstName(rs.getString(FIRST_NAME));
            user.setLastName(rs.getString(LAST_NAME));
            user.setPosition(rs.getString(POSITION));
            Brigade brigade = new Brigade();
            brigade.setBrigadeId(rs.getLong(BRIGADE_ID) == 0 ? null : rs.getLong(BRIGADE_ID));
            brigade.setBrigadeName(rs.getString(BRIGADE_NAME));
            WorkType workType = new WorkType();
            workType.setWorkTypeId(rs.getLong(BRIGADE_WORK_TYPE_ID) == 0 ? null : rs.getLong(BRIGADE_WORK_TYPE_ID));
            workType.setTypeName(rs.getString(BRIGADE_TYPE_NAME));
            brigade.setWorkType(workType);
            user.setBrigade(brigade);
            user.setStreet(rs.getString(STREET));
            user.setHouseNumber(rs.getString(HOUSE_NUMBER));
            user.setApartment(rs.getLong(APARTMENT));
            user.setCity(rs.getString(CITY));
            user.setPhoneNumber(rs.getString(PHONE_NUMBER));
            bid.setUserTenant(user);
            bid.setWorkScope(rs.getLong(WORK_SCOPE));
            workType = new WorkType();
            workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID) == 0 ? null : rs.getLong(WORK_TYPE_ID));
            workType.setTypeName(rs.getString(TYPE_NAME));
            bid.setWorkType(workType);

            bidList.add(bid);
        }

        return bidList;
    }

}
