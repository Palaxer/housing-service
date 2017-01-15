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
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLWorkPlaneDao}
 *
 * @author Taras Palashynskyy
 */

@RunWith(PowerMockRunner.class)
public class TestWorkPlaneDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    public TestWorkPlaneDao() {

    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.WorkPlane} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertWorkPlane() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        WorkPlane workPlane = new WorkPlane();
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));
        workPlane.setBrigade(new Brigade());
        workPlane.getBrigade().setBrigadeId(1L);
        workPlane.setStatus("status");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.now()));
        workPlane.setBid(new Bid());
        workPlane.getBid().setBidId(1L);
        workPlane.setUserAdvisor(new User());
        workPlane.getUserAdvisor().setUserId(1L);

        assertTrue(workPlaneDao.insertWorkPlane(workPlane));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setLong(1, workPlane.getBrigade().getBrigadeId());
        verify(mockPreparedStm, times(1)).setLong(2, workPlane.getBid().getBidId());
        verify(mockPreparedStm, times(1)).setString(3, workPlane.getStatus());
        verify(mockPreparedStm, times(1)).setLong(4, workPlane.getUserAdvisor().getUserId());
        verify(mockPreparedStm, times(1)).setTimestamp(5, workPlane.getWorkTime());
        verify(mockPreparedStm, times(1)).setTimestamp(6, workPlane.getCompleteTime());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(workPlane.getWorkPlaneId(), new Long(0));


    }

    /**
     * The method that checks whether the incorrect insertion of the {@link org.palax.entity.WorkPlane} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertWorkPlane() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        WorkPlane workPlane = new WorkPlane();
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.now()));
        workPlane.setBrigade(new Brigade());
        workPlane.getBrigade().setBrigadeId(1L);
        workPlane.setStatus("status");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.now()));
        workPlane.setBid(new Bid());
        workPlane.getBid().setBidId(1L);
        workPlane.setUserAdvisor(new User());
        workPlane.getUserAdvisor().setUserId(1L);

        assertFalse(workPlaneDao.insertWorkPlane(workPlane));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setLong(1, workPlane.getBrigade().getBrigadeId());
        verify(mockPreparedStm, times(1)).setLong(2, workPlane.getBid().getBidId());
        verify(mockPreparedStm, times(1)).setString(3, workPlane.getStatus());
        verify(mockPreparedStm, times(1)).setLong(4, workPlane.getUserAdvisor().getUserId());
        verify(mockPreparedStm, times(1)).setTimestamp(5, workPlane.getWorkTime());
        verify(mockPreparedStm, times(1)).setTimestamp(6, workPlane.getCompleteTime());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertEquals(workPlane.getWorkPlaneId(), null);


    }
}
