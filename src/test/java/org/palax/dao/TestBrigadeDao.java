package org.palax.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Brigade;
import org.palax.entity.WorkType;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

/**
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLBrigadeDao}
 *
 * @author Taras Palashynskyy
 */

@RunWith(PowerMockRunner.class)
public class TestBrigadeDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    public TestBrigadeDao() {

    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.Brigade} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertBrigade() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        Brigade brigade = new Brigade();
        brigade.setBrigadeName("brigade");
        brigade.setWorkType(new WorkType());
        brigade.getWorkType().setWorkTypeId(1L);

        assertTrue(brigadeDao.insertBrigade(brigade));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, brigade.getBrigadeName());
        verify(mockPreparedStm, times(1)).setLong(2, brigade.getWorkType().getWorkTypeId());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(brigade.getBrigadeId(), new Long(0));

    }

    /**
     * The method that checks whether the incorrect insertion of the {@link org.palax.entity.Brigade} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertBrigade() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        Brigade brigade = new Brigade();
        brigade.setBrigadeName("brigade");
        brigade.setWorkType(new WorkType());
        brigade.getWorkType().setWorkTypeId(1L);

        assertFalse(brigadeDao.insertBrigade(brigade));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, brigade.getBrigadeName());
        verify(mockPreparedStm, times(1)).setLong(2, brigade.getWorkType().getWorkTypeId());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertEquals(brigade.getBrigadeId(), null);

    }
}
