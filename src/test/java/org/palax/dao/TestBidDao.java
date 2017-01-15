package org.palax.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.*;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

/**
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLBidDao}
 *
 * @author Taras Palashynskyy
 */

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

    public TestBidDao() {

    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.Bid} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertBid() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        Bid bid = new Bid();
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setStatus("status");
        bid.setWorkScope(1L);
        bid.setWorkType(new WorkType());
        bid.getWorkType().setWorkTypeId(1L);
        bid.setDescription("desc");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(1L);

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
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertBid() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        BidDao bidDao = MySQLDAOFactory.getBidDao();

        Bid bid = new Bid();
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setStatus("status");
        bid.setWorkScope(1L);
        bid.setWorkType(new WorkType());
        bid.getWorkType().setWorkTypeId(1L);
        bid.setDescription("desc");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.now()));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(1L);

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

        assertEquals(bid.getBidId(), null);

    }
}
