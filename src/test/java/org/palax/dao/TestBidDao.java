package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.util.*;
import org.palax.entity.*;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.naming.InitialContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLBidDao}
 *
 * @author Taras Palashynskyy
 */
@PrepareForTest({DataSourceManager.class})
@RunWith(PowerMockRunner.class)
public class TestBidDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    /**
     * Set up method which inject {@link InitialContext} to the test environment
     * alse create and fill database
     */
    @BeforeClass
    public static void setUpClass() {
        TestDatabaseManager.setUpTestDatabase();
    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.Bid} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testCorrectInsertBid() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        Bid bid = DataGenerator.generateBid(1);

        assertTrue(bidDao.insertBid(bid));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setLong(1, bid.getWorkType().getWorkTypeId());
        verify(mockPreparedStm, times(1)).setLong(2, bid.getWorkScope());
        verify(mockPreparedStm, times(1)).setTimestamp(3, bid.getLeadTime());
        verify(mockPreparedStm, times(1)).setLong(4, bid.getUserTenant().getUserId());
        verify(mockPreparedStm, times(1)).setString(5, bid.getStatus());
        verify(mockPreparedStm, times(1)).setString(6, bid.getDescription());
        verify(mockPreparedStm, times(1)).setTimestamp(7, bid.getBidTime());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(bid.getBidId(), new Long(0));

    }

    /**
     * The method that checks whether the incorrect insertion of the {@link org.palax.entity.Bid} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testIncorrectInsertBid() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        Bid bid = DataGenerator.generateBid(1);
        bid.setBidId(null);

        assertFalse(bidDao.insertBid(bid));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setLong(1, bid.getWorkType().getWorkTypeId());
        verify(mockPreparedStm, times(1)).setLong(2, bid.getWorkScope());
        verify(mockPreparedStm, times(1)).setTimestamp(3, bid.getLeadTime());
        verify(mockPreparedStm, times(1)).setLong(4, bid.getUserTenant().getUserId());
        verify(mockPreparedStm, times(1)).setString(5, bid.getStatus());
        verify(mockPreparedStm, times(1)).setString(6, bid.getDescription());
        verify(mockPreparedStm, times(1)).setTimestamp(7, bid.getBidTime());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertNull(bid.getBidId());

    }

    /**
     * The method that checks successfully gets all {@link Bid} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetAllBid() throws SQLException {

        List<Bid> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateBid(i));
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
                return expectedList.get(i++).getBidId();
            }
        });
        when(mockResultSet.getTimestamp(24)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getBidTime();
            }
        });
        when(mockResultSet.getString(23)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getDescription();
            }
        });
        when(mockResultSet.getTimestamp(5)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getLeadTime();
            }
        });
        when(mockResultSet.getString(22)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getStatus();
            }
        });
        when(mockResultSet.getLong(6)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserTenant().getUserId();
            }
        });
        when(mockResultSet.getString(7)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getLogin();
            }
        });
        when(mockResultSet.getString(8)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getPassword();
            }
        });
        when(mockResultSet.getLong(9)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserTenant().getRole().getRoleId();
            }
        });
        when(mockResultSet.getString(10)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getRole().getRoleType();
            }
        });
        when(mockResultSet.getString(11)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getFirstName();
            }
        });
        when(mockResultSet.getString(12)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getLastName();
            }
        });
        when(mockResultSet.getString(13)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getPosition();
            }
        });
        when(mockResultSet.getLong(14)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserTenant().getBrigade().getBrigadeId();
            }
        });
        when(mockResultSet.getString(15)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getBrigade().getBrigadeName();
            }
        });
        when(mockResultSet.getLong(16)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserTenant().getBrigade().getWorkType().getWorkTypeId();
            }
        });
        when(mockResultSet.getString(17)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getBrigade().getWorkType().getTypeName();
            }
        });
        when(mockResultSet.getString(18)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getStreet();
            }
        });
        when(mockResultSet.getString(19)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getHouseNumber();
            }
        });
        when(mockResultSet.getLong(20)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getApartment();
            }
        });
        when(mockResultSet.getString(21)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getCity();
            }
        });
        when(mockResultSet.getLong(4)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getWorkScope();
            }
        });
        when(mockResultSet.getLong(2)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getWorkType().getWorkTypeId();
            }
        });
        when(mockResultSet.getString(3)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getWorkType().getTypeName();
            }
        });
        when(mockResultSet.getString(25)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getUserTenant().getPhoneNumber();
            }
        });

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        List<Bid> actualList = bidDao.getAllBid();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(3)).getLong(1);
        verify(mockResultSet, times(3)).getTimestamp(24);
        verify(mockResultSet, times(3)).getString(23);
        verify(mockResultSet, times(3)).getTimestamp(5);
        verify(mockResultSet, times(3)).getString(22);
        verify(mockResultSet, times(6)).getLong(6);
        verify(mockResultSet, times(3)).getString(7);
        verify(mockResultSet, times(3)).getString(8);
        verify(mockResultSet, times(6)).getLong(9);
        verify(mockResultSet, times(3)).getString(10);
        verify(mockResultSet, times(3)).getString(11);
        verify(mockResultSet, times(3)).getString(12);
        verify(mockResultSet, times(3)).getString(13);
        verify(mockResultSet, times(6)).getLong(14);
        verify(mockResultSet, times(3)).getString(15);
        verify(mockResultSet, times(6)).getLong(16);
        verify(mockResultSet, times(3)).getString(17);
        verify(mockResultSet, times(3)).getString(18);
        verify(mockResultSet, times(3)).getString(19);
        verify(mockResultSet, times(3)).getLong(20);
        verify(mockResultSet, times(3)).getString(21);
        verify(mockResultSet, times(3)).getLong(4);
        verify(mockResultSet, times(6)).getLong(2);
        verify(mockResultSet, times(3)).getString(3);
        verify(mockResultSet, times(3)).getString(25);

        assertEquals(actualList, expectedList);

    }

    /**
     * The method that checks successfully gets all {@link Bid} by {@code status} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetAllBidByStatus() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        List<Bid> expectedList = new ArrayList<>();
        WorkTypeBuilder workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(1L);
        RoleBuilder roleBuilder = RoleBuilder.getBuilder().constructRole(2L);
        BrigadeBuilder brigadeBuilder = BrigadeBuilder.getBuilder().constructBrigade(1L, workTypeBuilder);
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(3L, roleBuilder, brigadeBuilder);

        Bid bid = BidBuilder.getBuilder().constructBid(3L, "COMPLETE", workTypeBuilder, userBuilder).build();
        expectedList.add(bid);

        workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(4L);
        userBuilder = UserBuilder.getBuilder().constructUser(4L, roleBuilder, brigadeBuilder);

        bid = BidBuilder.getBuilder().constructBid(5L, "COMPLETE", workTypeBuilder, userBuilder).build();
        expectedList.add(bid);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        List<Bid> actualList = bidDao.getAllBidByStatus("COMPLETE");

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully gets all {@link Bid} by {@code userTenantId} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetAllBidByUserTenant() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        List<Bid> expectedList = new ArrayList<>();
        WorkTypeBuilder workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(null);
        RoleBuilder roleBuilder = RoleBuilder.getBuilder().constructRole(1L);
        BrigadeBuilder brigadeBuilder = BrigadeBuilder.getBuilder().constructBrigade(null, workTypeBuilder);
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(1L, roleBuilder, brigadeBuilder);
        workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(1L);

        Bid bid = BidBuilder.getBuilder().constructBid(1L, "NEW", workTypeBuilder, userBuilder).build();
        expectedList.add(bid);

        workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(3L);

        bid = BidBuilder.getBuilder().constructBid(4L, "IN WORK", workTypeBuilder, userBuilder).build();
        expectedList.add(bid);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        List<Bid> actualList = bidDao.getAllBidByUserTenant(1L);

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully gets {@link Bid} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetBidById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkTypeBuilder workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(null);
        RoleBuilder roleBuilder = RoleBuilder.getBuilder().constructRole(1L);
        BrigadeBuilder brigadeBuilder = BrigadeBuilder.getBuilder().constructBrigade(null, workTypeBuilder);
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(1L, roleBuilder, brigadeBuilder);
        workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(1L);

        Bid expectedBid = BidBuilder.getBuilder().constructBid(1L, "NEW", workTypeBuilder, userBuilder).build();

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        Bid actualBid = bidDao.getBidById(1L);

        assertEquals(expectedBid, actualBid);
    }

    /**
     * The method that checks successfully update {@link Bid} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testUpdateBid() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkTypeBuilder workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(null);
        RoleBuilder roleBuilder = RoleBuilder.getBuilder().constructRole(1L);
        BrigadeBuilder brigadeBuilder = BrigadeBuilder.getBuilder().constructBrigade(null, workTypeBuilder);
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(2L, roleBuilder, brigadeBuilder);
        workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(1L);

        Bid expectedBid = BidBuilder.getBuilder().constructBid(7L, "UPDATE", workTypeBuilder, userBuilder).build();

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        assertTrue(bidDao.updateBid(expectedBid));

        Bid actualBid = bidDao.getBidById(7L);

        assertEquals(expectedBid, actualBid);
    }

    /**
     * The method that checks successfully delete {@link Bid} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testDeleteBid() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(2L, null, null);

        Bid bid = BidBuilder.getBuilder().constructBid(6L, "IN WORK", null, userBuilder).build();

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        assertTrue(bidDao.deleteBid(bid));

        Bid actualBid = bidDao.getBidById(6L);

        assertNull(actualBid);
    }
}
