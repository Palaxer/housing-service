package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.util.DataGenerator;
import org.palax.dao.util.InjectingJNDIDataSource;
import org.palax.dao.util.TestDatabaseManager;
import org.palax.entity.Role;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * The {@code TestRoleDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLRoleDao}
 *
 * @author Taras Palashynskyy
 */

@RunWith(PowerMockRunner.class)
public class TestRoleDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    public TestRoleDao() {    }

    /**
     * Set up method which inject {@link InitialContext} to the test environment
     * alse create and fill database
     */
    @BeforeClass
    public static void setUpClass() {
        TestDatabaseManager.setUpTestDatabase();
    }

    /**
     * The method that checks whether the insertion of the {@link Role} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertRole() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        Role role = new Role();
        role.setRoleType("role");

        assertTrue(roleDao.insertRole(role));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, role.getRoleType());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(role.getRoleId(), new Long(0));

    }

    /**
     * The method that checks whether the incorrect insertion of the {@link Role} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertRole() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        Role role = new Role();
        role.setRoleType("role");

        assertFalse(roleDao.insertRole(role));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, role.getRoleType());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertEquals(role.getRoleId(), null);

    }

    /**
     * The method that checks successfully gets all {@link Role} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectGetAllRole() throws SQLException {

        List<Role> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateRole(i));
        }

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(1)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getRoleId();
            }
        });
        when(mockResultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getRoleType();
            }
        });

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        List<Role> actualList = roleDao.getAllRole();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(3)).getLong(1);
        verify(mockResultSet, times(3)).getString(2);

        assertEquals(actualList, expectedList);
    }

    /**
     * The method that checks successfully gets all {@link Role} by {@code roleType} name from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetRoleByName() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Role expectedRole = new Role();
        expectedRole.setRoleId(1L);
        expectedRole.setRoleType("role1");

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        Role actualRole = roleDao.getRoleByName("role1");

        assertEquals(expectedRole, actualRole);
    }

    /**
     * The method that checks successfully gets all {@link Role} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetRoleById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Role expectedRole = new Role();
        expectedRole.setRoleId(1L);
        expectedRole.setRoleType("role1");

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        Role actualRole = roleDao.getRoleById(1L);

        assertEquals(expectedRole, actualRole);
    }

    /**
     * The method that checks successfully update {@link Role} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testUpdateRole() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Role expectedRole = new Role();
        expectedRole.setRoleId(1L);
        expectedRole.setRoleType("update");

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        assertTrue(roleDao.updateRole(expectedRole));

        Role actualRole = roleDao.getRoleById(1L);

        assertEquals(expectedRole, actualRole);
    }

    /**
     * The method that checks successfully delete {@link Role} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testDeleteRole() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Role expectedRole = new Role();
        expectedRole.setRoleId(5L);
        expectedRole.setRoleType("role5");

        RoleDao roleDao = MySQLDAOFactory.getRoleDao();

        assertTrue(roleDao.deleteRole(expectedRole));

        assertNull(roleDao.getRoleById(5L));
    }
}
