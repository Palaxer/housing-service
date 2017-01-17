package org.palax.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.*;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

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
     * The method that checks whether the insertion of the {@link org.palax.entity.User} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
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
     *
     * @throws SQLException {@link SQLException}
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
     *
     * @throws SQLException {@link SQLException}
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
}
