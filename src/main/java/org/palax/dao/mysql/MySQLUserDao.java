package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.UserDao;
import org.palax.dao.transaction.TransactionUserDao;
import org.palax.entity.Brigade;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.entity.WorkType;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLUserDao} singleton class implements {@link UserDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLUserDao implements UserDao, TransactionUserDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLUserDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLUserDao mySQLUserDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int USER_ID = 1;
    private static final int LOGIN = 2;
    private static final int PASSWORD = 3;
    private static final int ROLE_ID = 4;
    private static final int ROLE_TYPE = 5;
    private static final int FIRST_NAME = 6;
    private static final int LAST_NAME = 7;
    private static final int POSITION = 8;
    private static final int BRIGADE_ID = 9;
    private static final int BRIGADE_NAME = 10;
    private static final int WORK_TYPE_ID = 11;
    private static final int TYPE_NAME = 12;
    private static final int STREET = 13;
    private static final int HOUSE_NUMBER = 14;
    private static final int APARTMENT = 15;
    private static final int CITY = 16;
    private static final int PHONE_NUMBER = 17;

    private MySQLUserDao() {

    }

    /**
     * Always return same {@link MySQLUserDao} instance
     *
     * @return always return same {@link MySQLUserDao} instance
     */
    public synchronized static MySQLUserDao getInstance() {
        if(mySQLUserDao == null) {
            mySQLUserDao = new MySQLUserDao();
            logger.debug("Create first MySQLUserDao instance");
        }
        logger.debug("Return MySQLUserDao instance");
        return mySQLUserDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUser() {
        Connection con = DataSourceManager.getConnection();
        List<User> userList = getAllUserTransaction(con);
        DataSourceManager.closeAll(con, null, null);

        return userList;
    }

    @Override
    public List<User> getAllUser(int offSet, int numberOfElement) {
        String SQL = "SELECT A.USER_ID, A.LOGIN, A.PASSWD, A.ROLE_ID, B.ROLE_TYPE, A.FIRST_NAME, " +
                "A.LAST_NAME, A.POSITION, A.BRIGADE_ID, C.BRIGADE_NAME, C.WORK_TYPE_ID, " +
                "D.TYPE_NAME, A.STREET, A.HOUSE_NUMBER, A.APARTMENT, A.CITY, A.PHONE_NUMBER " +
                "FROM user A " +
                "LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LEFT JOIN brigade C ON (A.BRIGADE_ID=C.BRIGADE_ID) " +
                "LEFT JOIN work_type D ON (C.WORK_TYPE_ID=D.WORK_TYPE_ID) " +
                "LIMIT " + offSet +", " + numberOfElement;

        List<User> userList = null;



        Connection con = DataSourceManager.getConnection();

        try(Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(SQL)) {

            userList = parseResultSet(rs);

            logger.debug("Get all USER successfully " + userList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, null, null);
        }
        logger.debug("Try get all USER     " + offSet  +"   " + numberOfElement + "  sizze " + userList.size());
        return userList;
    }

    @Override
    public Long getTableRowSize() {
        String SQL = "SELECT COUNT(*) FROM user";


        Connection con = DataSourceManager.getConnection();
        Long count = null;

        try(Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(SQL)) {

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Try count all USER" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(con, null, null);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUserTransaction(Connection con) {
        String SQL = "SELECT A.USER_ID, A.LOGIN, A.PASSWD, A.ROLE_ID, B.ROLE_TYPE, A.FIRST_NAME, " +
                "A.LAST_NAME, A.POSITION, A.BRIGADE_ID, C.BRIGADE_NAME, C.WORK_TYPE_ID, " +
                "D.TYPE_NAME, A.STREET, A.HOUSE_NUMBER, A.APARTMENT, A.CITY, A.PHONE_NUMBER " +
                "FROM user A " +
                "LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LEFT JOIN brigade C ON (A.BRIGADE_ID=C.BRIGADE_ID) " +
                "LEFT JOIN work_type D ON (C.WORK_TYPE_ID=D.WORK_TYPE_ID)";

        List<User> userList = null;

        logger.debug("Try get all USER");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            userList = parseResultSet(rs);

            logger.debug("Get all USER successfully " + userList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return userList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUserByBrigadeId(Long id) {
        Connection con = DataSourceManager.getConnection();
        List<User> userList = getAllUserByBrigadeIdTransacion(id, con);
        DataSourceManager.closeAll(con, null, null);

        return userList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUserByBrigadeIdTransacion(Long id, Connection con) {
        String SQL = "SELECT A.USER_ID, A.LOGIN, A.PASSWD, A.ROLE_ID, B.ROLE_TYPE, A.FIRST_NAME, " +
                "A.LAST_NAME, A.POSITION, A.BRIGADE_ID, C.BRIGADE_NAME, C.WORK_TYPE_ID, " +
                "D.TYPE_NAME, A.STREET, A.HOUSE_NUMBER, A.APARTMENT, A.CITY, A.PHONE_NUMBER " +
                "FROM user A " +
                "LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LEFT JOIN brigade C ON (A.BRIGADE_ID=C.BRIGADE_ID) " +
                "LEFT JOIN work_type D ON (C.WORK_TYPE_ID=D.WORK_TYPE_ID) " +
                "WHERE A.BRIGADE_ID=?";

        List<User> userList = null;

        logger.debug("Try get all USER");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            userList = parseResultSet(rs);

            logger.debug("Get all USER successfully " + userList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return userList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) {
        Connection con = DataSourceManager.getConnection();
        User user = getUserByIdTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByIdTransaction(Long id, Connection con) {
        String SQL = "SELECT A.USER_ID, A.LOGIN, A.PASSWD, A.ROLE_ID, B.ROLE_TYPE, A.FIRST_NAME, " +
                "A.LAST_NAME, A.POSITION, A.BRIGADE_ID, C.BRIGADE_NAME, C.WORK_TYPE_ID, " +
                "D.TYPE_NAME, A.STREET, A.HOUSE_NUMBER, A.APARTMENT, A.CITY, A.PHONE_NUMBER " +
                "FROM user A " +
                "LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LEFT JOIN brigade C ON (A.BRIGADE_ID=C.BRIGADE_ID) " +
                "LEFT JOIN work_type D ON (C.WORK_TYPE_ID=D.WORK_TYPE_ID) " +
                "WHERE A.USER_ID=?";

        User user = null;

        logger.debug("Try get USER by ID " + id);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            List<User> userList = parseResultSet(rs);
            user = userList.isEmpty() ? null : userList.get(0);

            logger.debug("Get USER by ID successfully " + user);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByLogin(String login) {
        Connection con = DataSourceManager.getConnection();
        User user = getUserByLoginTransaction(login, con);
        DataSourceManager.closeAll(con, null, null);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByLoginTransaction(String login, Connection con) {
        String SQL = "SELECT A.USER_ID, A.LOGIN, A.PASSWD, A.ROLE_ID, B.ROLE_TYPE, A.FIRST_NAME, " +
                "A.LAST_NAME, A.POSITION, A.BRIGADE_ID, C.BRIGADE_NAME, C.WORK_TYPE_ID, " +
                "D.TYPE_NAME, A.STREET, A.HOUSE_NUMBER, A.APARTMENT, A.CITY, A.PHONE_NUMBER " +
                "FROM user A " +
                "LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LEFT JOIN brigade C ON (A.BRIGADE_ID=C.BRIGADE_ID) " +
                "LEFT JOIN work_type D ON (C.WORK_TYPE_ID=D.WORK_TYPE_ID) " +
                "WHERE A.LOGIN=?";

        User user = null;

        logger.debug("Try get USER by NAME " + login);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, login);
            rs = stm.executeQuery();

            List<User> userList = parseResultSet(rs);
            user = userList.isEmpty() ? null : userList.get(0);

            logger.debug("Get USER by NAME successfully " + user);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUser(User user) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = deleteUserTransaction(user, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteUserTransaction(User user, Connection con) {
        String SQL = "DELETE FROM user WHERE USER_ID=?";

        logger.debug("Try delete USER " + user);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, user.getUserId());
            if(stm.executeUpdate() > 0) {
                logger.debug("USER delete successfully " + user);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("USER delete fail " + user);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUser(User user) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = updateUserTransaction(user, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUserTransaction(User user, Connection con) {
        String SQL = "UPDATE user SET LOGIN=?, PASSWD=?, ROLE_ID=?, FIRST_NAME=?, LAST_NAME=?, POSITION=?," +
                " BRIGADE_ID=?, STREET=?, HOUSE_NUMBER=?, APARTMENT=?, CITY=?, PHONE_NUMBER=? WHERE USER_ID=?";

        logger.debug("Try update USER " + user);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            Long roleId = user.getRole() != null ? user.getRole().getRoleId() : null;
            if(roleId == null)
                stm.setNull(3,Types.INTEGER);
            else
                stm.setLong(3, roleId);
            stm.setString(4, user.getFirstName());
            stm.setString(5, user.getLastName());
            stm.setString(6, user.getPosition());
            Long brigadeId = user.getBrigade() != null ? user.getBrigade().getBrigadeId() : null;
            if(brigadeId == null)
                stm.setNull(7,Types.INTEGER);
            else
                stm.setLong(7, brigadeId);
            stm.setString(8, user.getStreet());
            stm.setString(9, user.getHouseNumber());
            stm.setLong(10, user.getApartment());
            stm.setString(11, user.getCity());
            stm.setString(12, user.getPhoneNumber());
            stm.setLong(13, user.getUserId());
            if(stm.executeUpdate() > 0) {
                logger.debug("USER update successfully " + user);
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try update new USER " + user);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("USER update fail " + user);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertUser(User user) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = insertUserTransaction(user, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertUserTransaction(User user, Connection con) {
        String SQL = "INSERT INTO user (LOGIN, PASSWD, ROLE_ID, FIRST_NAME, LAST_NAME, POSITION," +
                " BRIGADE_ID, STREET, HOUSE_NUMBER, APARTMENT, CITY, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        logger.debug("Try insert USER " + user);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            Long roleId = user.getRole() != null ? user.getRole().getRoleId() : null;
            if(roleId == null)
                stm.setNull(3,Types.INTEGER);
            else
                stm.setLong(3, roleId);
            stm.setString(4, user.getFirstName());
            stm.setString(5, user.getLastName());
            stm.setString(6, user.getPosition());
            Long brigadeId = user.getBrigade() != null ? user.getBrigade().getBrigadeId() : null;
            if(brigadeId == null)
                stm.setNull(7,Types.INTEGER);
            else
                stm.setLong(7, brigadeId);
            stm.setString(8, user.getStreet());
            stm.setString(9, user.getHouseNumber());
            stm.setLong(10, user.getApartment());
            stm.setString(11, user.getCity());
            stm.setString(12, user.getPhoneNumber());

            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    user.setUserId(rs.getLong(1));
                    logger.debug("USER insert successfully " + user);
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try create new USER " + user);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("USER insert fail " + user);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link List} of {@link User}
     *
     * @param rs {@link ResultSet} which be persed
     * @return parse {@code rs} and return {@link List} of {@link User}
     */
    private List<User> parseResultSet(ResultSet rs) throws SQLException {
        List<User> userList = new ArrayList<>();
        User user;
        while(rs.next()) {
            user = new User();

            user.setUserId(rs.getLong(USER_ID) == 0 ? null : rs.getLong(USER_ID));
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
            workType.setWorkTypeId(rs.getLong(WORK_TYPE_ID) == 0 ? null : rs.getLong(WORK_TYPE_ID));
            workType.setTypeName(rs.getString(TYPE_NAME));
            brigade.setWorkType(workType);
            user.setBrigade(brigade);
            user.setStreet(rs.getString(STREET));
            user.setHouseNumber(rs.getString(HOUSE_NUMBER));
            user.setApartment(rs.getLong(APARTMENT));
            user.setCity(rs.getString(CITY));
            user.setPhoneNumber(rs.getString(PHONE_NUMBER));

            userList.add(user);
        }

        return userList;
    }
}
