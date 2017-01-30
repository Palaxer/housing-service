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
import org.palax.entity.Brigade;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.entity.WorkType;
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
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLUserDao}
 *
 * @author Taras Palashynskyy
 */

@RunWith(PowerMockRunner.class)
public class TestUserDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    public TestUserDao() {

    }

    /**
     * Set up method which inject {@link InitialContext} to the test environment
     * alse create and fill database
     */
    @BeforeClass
    public static void setUpClass() {
        TestDatabaseManager.setUpTestDatabase();
    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.User} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertUser() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        UserDao userDao = MySQLDAOFactory.getUserDao();

        User user = new User();
        user.setStreet("street");
        user.setPassword("passwd");
        user.setHouseNumber("10/2");
        user.setLastName("last name");
        user.setApartment(1L);
        user.setCity("city");
        user.setFirstName("first name");
        user.setLogin("login");
        user.setPosition("position");
        user.setPhoneNumber("093-722-9393");
        user.setRole(new Role());
        user.getRole().setRoleId(1L);
        user.setBrigade(new Brigade());
        user.getBrigade().setBrigadeId(1L);

        assertTrue(userDao.insertUser(user));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, user.getLogin());
        verify(mockPreparedStm, times(1)).setString(2, user.getPassword());
        verify(mockPreparedStm, times(1)).setLong(3, user.getRole().getRoleId());
        verify(mockPreparedStm, times(1)).setString(4, user.getFirstName());
        verify(mockPreparedStm, times(1)).setString(5, user.getLastName());
        verify(mockPreparedStm, times(1)).setString(6, user.getPosition());
        verify(mockPreparedStm, times(1)).setLong(7, user.getBrigade().getBrigadeId());
        verify(mockPreparedStm, times(1)).setString(8, user.getStreet());
        verify(mockPreparedStm, times(1)).setString(9, user.getHouseNumber());
        verify(mockPreparedStm, times(1)).setLong(10, user.getApartment());
        verify(mockPreparedStm, times(1)).setString(11, user.getCity());
        verify(mockPreparedStm, times(1)).setString(12, user.getPhoneNumber());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(user.getUserId(), new Long(0));


    }

    /**
     * The method that checks whether the incorrect insertion of the {@link org.palax.entity.User} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertUser() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        UserDao userDao = MySQLDAOFactory.getUserDao();

        User user = new User();
        user.setStreet("street");
        user.setPassword("passwd");
        user.setHouseNumber("10/2");
        user.setLastName("last name");
        user.setApartment(1L);
        user.setCity("city");
        user.setFirstName("first name");
        user.setLogin("login");
        user.setPosition("position");
        user.setPhoneNumber("093-722-9393");
        user.setRole(new Role());
        user.getRole().setRoleId(1L);
        user.setBrigade(new Brigade());
        user.getBrigade().setBrigadeId(1L);

        assertFalse(userDao.insertUser(user));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, user.getLogin());
        verify(mockPreparedStm, times(1)).setString(2, user.getPassword());
        verify(mockPreparedStm, times(1)).setLong(3, user.getRole().getRoleId());
        verify(mockPreparedStm, times(1)).setString(4, user.getFirstName());
        verify(mockPreparedStm, times(1)).setString(5, user.getLastName());
        verify(mockPreparedStm, times(1)).setString(6, user.getPosition());
        verify(mockPreparedStm, times(1)).setLong(7, user.getBrigade().getBrigadeId());
        verify(mockPreparedStm, times(1)).setString(8, user.getStreet());
        verify(mockPreparedStm, times(1)).setString(9, user.getHouseNumber());
        verify(mockPreparedStm, times(1)).setLong(10, user.getApartment());
        verify(mockPreparedStm, times(1)).setString(11, user.getCity());
        verify(mockPreparedStm, times(1)).setString(12, user.getPhoneNumber());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertEquals(user.getUserId(), null);

    }

    /**
     * The method that checks successfuly gets all {@link User} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectGetAllUser() throws SQLException {

        List<User> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateUser(i));
        }

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(1)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserId();
            }
        });
        when(mockResultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getLogin();
            }
        });
        when(mockResultSet.getString(3)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getPassword();
            }
        });
        when(mockResultSet.getLong(4)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getRole().getRoleId();
            }
        });
        when(mockResultSet.getString(5)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getRole().getRoleType();
            }
        });
        when(mockResultSet.getString(6)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getFirstName();
            }
        });
        when(mockResultSet.getString(7)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getLastName();
            }
        });
        when(mockResultSet.getString(8)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getPosition();
            }
        });
        when(mockResultSet.getLong(9)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getBrigade().getBrigadeId();
            }
        });
        when(mockResultSet.getString(10)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getBrigade().getBrigadeName();
            }
        });
        when(mockResultSet.getLong(11)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getBrigade().getWorkType().getWorkTypeId();
            }
        });
        when(mockResultSet.getString(12)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getBrigade().getWorkType().getTypeName();
            }
        });
        when(mockResultSet.getString(13)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getStreet();
            }
        });
        when(mockResultSet.getString(14)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getHouseNumber();
            }
        });
        when(mockResultSet.getLong(15)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getApartment();
            }
        });
        when(mockResultSet.getString(16)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getCity();
            }
        });
        when(mockResultSet.getString(17)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getPhoneNumber();
            }
        });

        UserDao userDao = MySQLDAOFactory.getUserDao();

        List<User> actualList = userDao.getAllUser();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(6)).getLong(1);
        verify(mockResultSet, times(3)).getString(2);
        verify(mockResultSet, times(3)).getString(3);
        verify(mockResultSet, times(6)).getLong(4);
        verify(mockResultSet, times(3)).getString(5);
        verify(mockResultSet, times(3)).getString(6);
        verify(mockResultSet, times(3)).getString(7);
        verify(mockResultSet, times(3)).getString(8);
        verify(mockResultSet, times(6)).getLong(9);
        verify(mockResultSet, times(3)).getString(10);
        verify(mockResultSet, times(6)).getLong(11);
        verify(mockResultSet, times(3)).getString(12);
        verify(mockResultSet, times(3)).getString(13);
        verify(mockResultSet, times(3)).getString(14);
        verify(mockResultSet, times(3)).getLong(15);
        verify(mockResultSet, times(3)).getString(16);
        verify(mockResultSet, times(3)).getString(17);

        assertEquals(actualList, expectedList);
    }

    /**
     * The method that checks successfully gets all {@link User} by {@code brigadeId} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetAllUserByBrigadeId() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        List<User> expectedList = new ArrayList<>();
        User user = new User();
        WorkType workType = new WorkType();
        workType.setWorkTypeId(1L);
        workType.setTypeName("work_type1");
        user.setUserId(3L);
        user.setLogin("login3");
        user.setPassword("passwd3");
        user.setRole(new Role());
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name3");
        user.setLastName("last_name3");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        user.getBrigade().setWorkType(workType);
        user.setStreet("str3");
        user.setPosition("pos1");
        user.setHouseNumber("3");
        user.setApartment(3L);
        user.setCity("city2");
        user.setPhoneNumber("093-722-9394");
        expectedList.add(user);

        user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        user.setRole(new Role());
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setBrigade(brigade);
        user.setStreet("str4");
        user.setPosition("pos2");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        expectedList.add(user);

        UserDao userDao = MySQLDAOFactory.getUserDao();

        List<User> actualList = userDao.getAllUserByBrigadeId(1L);

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully get {@link User} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetUserById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        User expectedUser = new User();
        WorkType workType = new WorkType();
        workType.setWorkTypeId(1L);
        workType.setTypeName("work_type1");
        expectedUser.setUserId(3L);
        expectedUser.setLogin("login3");
        expectedUser.setPassword("passwd3");
        expectedUser.setRole(new Role());
        expectedUser.getRole().setRoleId(2L);
        expectedUser.getRole().setRoleType("role2");
        expectedUser.setFirstName("first_name3");
        expectedUser.setLastName("last_name3");
        Brigade brigade = new Brigade();
        expectedUser.setBrigade(brigade);
        expectedUser.getBrigade().setBrigadeId(1L);
        expectedUser.getBrigade().setBrigadeName("brigade1");
        expectedUser.getBrigade().setWorkType(workType);
        expectedUser.setStreet("str3");
        expectedUser.setPosition("pos1");
        expectedUser.setHouseNumber("3");
        expectedUser.setApartment(3L);
        expectedUser.setCity("city2");
        expectedUser.setPhoneNumber("093-722-9394");

        UserDao userDao = MySQLDAOFactory.getUserDao();

        User actualUser = userDao.getUserById(3L);

        assertEquals(expectedUser, actualUser);
    }

    /**
     * The method that checks successfully get {@link User} by {@code login} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetUserByLogin() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        User expectedUser = new User();
        WorkType workType = new WorkType();
        workType.setWorkTypeId(1L);
        workType.setTypeName("work_type1");
        expectedUser.setUserId(3L);
        expectedUser.setLogin("login3");
        expectedUser.setPassword("passwd3");
        expectedUser.setRole(new Role());
        expectedUser.getRole().setRoleId(2L);
        expectedUser.getRole().setRoleType("role2");
        expectedUser.setFirstName("first_name3");
        expectedUser.setLastName("last_name3");
        Brigade brigade = new Brigade();
        expectedUser.setBrigade(brigade);
        expectedUser.getBrigade().setBrigadeId(1L);
        expectedUser.getBrigade().setBrigadeName("brigade1");
        expectedUser.getBrigade().setWorkType(workType);
        expectedUser.setStreet("str3");
        expectedUser.setPosition("pos1");
        expectedUser.setHouseNumber("3");
        expectedUser.setApartment(3L);
        expectedUser.setCity("city2");
        expectedUser.setPhoneNumber("093-722-9394");

        UserDao userDao = MySQLDAOFactory.getUserDao();

        User actualUser = userDao.getUserByLogin("login3");

        assertEquals(expectedUser, actualUser);
    }

    /**
     * The method that checks successfully update {@link User} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testUpdateUser() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        User expectedUser = new User();
        WorkType workType = new WorkType();
        workType.setWorkTypeId(1L);
        workType.setTypeName("work_type1");
        expectedUser.setUserId(3L);
        expectedUser.setLogin("update");
        expectedUser.setPassword("passwd3");
        expectedUser.setRole(new Role());
        expectedUser.getRole().setRoleId(2L);
        expectedUser.getRole().setRoleType("role2");
        expectedUser.setFirstName("first_name3");
        expectedUser.setLastName("last_name3");
        Brigade brigade = new Brigade();
        expectedUser.setBrigade(brigade);
        expectedUser.getBrigade().setBrigadeId(1L);
        expectedUser.getBrigade().setBrigadeName("brigade1");
        expectedUser.getBrigade().setWorkType(workType);
        expectedUser.setStreet("str3");
        expectedUser.setPosition("pos1");
        expectedUser.setHouseNumber("3");
        expectedUser.setApartment(3L);
        expectedUser.setCity("city2");
        expectedUser.setPhoneNumber("093-722-9394");

        UserDao userDao = MySQLDAOFactory.getUserDao();

        assertTrue(userDao.updateUser(expectedUser));

        User actualUser = userDao.getUserById(3L);

        assertEquals(expectedUser, actualUser);
    }

    /**
     * The method that checks successfully delete {@link User} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testDeleteUser() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        User expectedUser = new User();
        expectedUser.setUserId(7L);
        expectedUser.setLogin("login7");
        expectedUser.setPassword("passwd3");
        expectedUser.setRole(new Role());
        expectedUser.getRole().setRoleId(2L);
        expectedUser.getRole().setRoleType("role2");
        expectedUser.setFirstName("first_name7");
        expectedUser.setLastName("last_name7");
        expectedUser.setStreet("str7");
        expectedUser.setHouseNumber("7");
        expectedUser.setApartment(7L);
        expectedUser.setCity("city2");
        expectedUser.setPhoneNumber("093-722-9394");

        UserDao userDao = MySQLDAOFactory.getUserDao();

        assertTrue(userDao.deleteUser(expectedUser));

        assertNull(userDao.getUserById(7L));
    }
}
