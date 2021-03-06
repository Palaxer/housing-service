package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.RoleDao;
import org.palax.dao.transaction.TransactionRoleDao;
import org.palax.entity.Role;
import org.palax.utils.DataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLRoleDao} singleton class implements {@link RoleDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLRoleDao implements RoleDao, TransactionRoleDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLRoleDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static MySQLRoleDao mySQLRoleDao;
    /**Values which store column id for {@link ResultSet} */
    private static final int ROLE_ID = 1;
    private static final int ROLE_TYPE = 2;

    private MySQLRoleDao() {

    }

    /**
     * Always return same {@link MySQLRoleDao} instance
     *
     * @return always return same {@link MySQLRoleDao} instance
     */
    public synchronized static MySQLRoleDao getInstance() {
        if(mySQLRoleDao == null) {
            mySQLRoleDao = new MySQLRoleDao();
            logger.debug("Create first MySQLRoleDao instance");
        }
        logger.debug("Return MySQLRoleDao instance");
        return mySQLRoleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> getAllRole() {
        Connection con = DataSourceManager.getConnection();
        List<Role> roleList = getAllRoleTransaction(con);
        DataSourceManager.closeAll(con, null, null);

        return roleList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> getAllRoleTransaction(Connection con) {
        String SQL = "SELECT * FROM role";

        ArrayList<Role> roleList = new ArrayList<>();
        Role role;

        logger.debug("Try get all ROLE");

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            rs = stm.executeQuery();

            while(rs.next()) {
                role = new Role();

                role.setRoleId(rs.getLong(ROLE_ID));
                role.setRoleType(rs.getString(ROLE_TYPE));

                roleList.add(role);
            }
            logger.debug("Get all ROLE successfully " + roleList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return roleList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleByName(String name) {
        Connection con = DataSourceManager.getConnection();
        Role role = getRoleByNameTransaction(name, con);
        DataSourceManager.closeAll(con, null, null);

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleByNameTransaction(String name, Connection con) {
        String SQL = "SELECT * FROM role WHERE ROLE_TYPE=?";
        Role role = null;

        logger.debug("Try get ROLE by NAME " + name);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, name);
            rs = stm.executeQuery();

            while(rs.next()) {
                role = new Role();

                role.setRoleId(rs.getLong(ROLE_ID));
                role.setRoleType(rs.getString(ROLE_TYPE));
            }
            logger.debug("Get ROLE by NAME successfulyl " + role);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleById(Long id) {
        Connection con = DataSourceManager.getConnection();
        Role role = getRoleByIdTransaction(id, con);
        DataSourceManager.closeAll(con, null, null);

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleByIdTransaction(Long id, Connection con) {
        String SQL = "SELECT * FROM role WHERE ROLE_ID=?";
        Role role = null;

        logger.debug("Try get ROLE by ID " + id);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            while(rs.next()) {
                role = new Role();

                role.setRoleId(rs.getLong(ROLE_ID));
                role.setRoleType(rs.getString(ROLE_TYPE));
            }
            logger.debug("Get ROLE by ID successfully " + role);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, rs);
        }

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteRole(Role role) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = deleteRoleTransaction(role, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteRoleTransaction(Role role, Connection con) {
        String SQL = "DELETE FROM role WHERE ROLE_ID=?";

        logger.debug("Try delete ROLE " + role);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setLong(1, role.getRoleId());
            if(stm.executeUpdate() > 0) {
                logger.debug("ROLE delete successfully " + role);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("ROLE delete fail " + role);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRole(Role role) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = updateRoleTransaction(role, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateRoleTransaction(Role role, Connection con) {
        String SQL = "UPDATE role SET ROLE_TYPE=? WHERE ROLE_ID=?";

        logger.debug("Try update ROLE " + role);

        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(SQL);
            stm.setString(1, role.getRoleType());
            stm.setLong(2, role.getRoleId());
            if(stm.executeUpdate() > 0) {
                logger.debug("ROLE update successfully " + role);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("ROLE update fail " + role);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertRole(Role role) {
        Connection con = DataSourceManager.getConnection();
        Boolean aBoolean = insertRoleTransaction(role, con);
        DataSourceManager.closeAll(con, null, null);

        return aBoolean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertRoleTransaction(Role role, Connection con) {
        String SQL = "INSERT INTO role (ROLE_TYPE)  " +
                "VALUES (?)";

        logger.debug("Try insert ROLE " + role);

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, role.getRoleType());
            if(stm.executeUpdate() > 0) {
                rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    role.setRoleId(rs.getLong(1));
                    logger.debug("ROLE insert successfully " + role);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        } finally {
            DataSourceManager.closeAll(null, stm, null);
        }
        logger.debug("ROLE insert fail " + role);
        return false;
    }
}
