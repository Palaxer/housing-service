package org.palax.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.mysql.MySQLBidDao;
import org.palax.dao.mysql.MySQLBrigadeDao;
import org.palax.dao.mysql.MySQLUserDao;
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
import static org.mockito.Matchers.anyLong;
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
    @Mock
    private MySQLUserDao mockUserDao;
    @Mock
    private MySQLBrigadeDao mockBrigadeDao;
    @Mock
    private MySQLBidDao mockBidDao;

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

    /**
     * The method that checks successfuly gets all {@link WorkPlane} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     *
     * @throws SQLException {@link SQLException}
     */
    @PrepareForTest({DataSourceManager.class, MySQLDAOFactory.class})
    @Test
    public void testCorrectGetAllWorkPlane() throws SQLException {

        List<WorkPlane> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateWorkPlane(i));
        }

        PowerMockito.mockStatic(DataSourceManager.class);
        PowerMockito.mockStatic(MySQLDAOFactory.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        PowerMockito.when(MySQLDAOFactory.getUserDao()).thenReturn(mockUserDao);
        PowerMockito.when(MySQLDAOFactory.getBrigadeDao()).thenReturn(mockBrigadeDao);
        PowerMockito.when(MySQLDAOFactory.getBidDao()).thenReturn(mockBidDao);
        PowerMockito.when(MySQLDAOFactory.getWorkPlaneDao()).thenCallRealMethod();
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        when(mockUserDao.getUserById(expectedList.get(0).getUserAdvisor().getUserId()))
                .thenReturn(expectedList.get(0).getUserAdvisor());
        when(mockUserDao.getUserById(expectedList.get(1).getUserAdvisor().getUserId()))
                .thenReturn(expectedList.get(1).getUserAdvisor());
        when(mockUserDao.getUserById(expectedList.get(2).getUserAdvisor().getUserId()))
                .thenReturn(expectedList.get(2).getUserAdvisor());
        when(mockBrigadeDao.getBrigadeById(expectedList.get(0).getBrigade().getBrigadeId()))
                .thenReturn(expectedList.get(0).getBrigade());
        when(mockBrigadeDao.getBrigadeById(expectedList.get(1).getBrigade().getBrigadeId()))
                .thenReturn(expectedList.get(1).getBrigade());
        when(mockBrigadeDao.getBrigadeById(expectedList.get(2).getBrigade().getBrigadeId()))
                .thenReturn(expectedList.get(2).getBrigade());
        when(mockBidDao.getBidById(expectedList.get(0).getBid().getBidId()))
                .thenReturn(expectedList.get(0).getBid());
        when(mockBidDao.getBidById(expectedList.get(1).getBid().getBidId()))
                .thenReturn(expectedList.get(1).getBid());
        when(mockBidDao.getBidById(expectedList.get(2).getBid().getBidId()))
                .thenReturn(expectedList.get(2).getBid());
        when(mockResultSet.getLong(1)).thenAnswer(new Answer<Long>() {
            int i = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getWorkPlaneId();
            }
        });
        when(mockResultSet.getLong(2)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getUserAdvisor().getUserId();
            }
        });
        when(mockResultSet.getLong(3)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getBid().getBidId();
            }
        });
        when(mockResultSet.getLong(4)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getBid().getBidId();
            }
        });
        when(mockResultSet.getString(5)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getStatus();
            }
        });
        when(mockResultSet.getTimestamp(6)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getWorkTime();
            }
        });
        when(mockResultSet.getTimestamp(7)).thenAnswer(new Answer<Timestamp>() {
            int i = 0;

            @Override
            public Timestamp answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getCompleteTime();
            }
        });

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        List<WorkPlane> actualList = workPlaneDao.getAllWorkPlane();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(3)).getLong(1);
        verify(mockResultSet, times(6)).getLong(2);
        verify(mockResultSet, times(6)).getLong(3);
        verify(mockResultSet, times(6)).getLong(4);
        verify(mockResultSet, times(3)).getString(5);
        verify(mockResultSet, times(3)).getTimestamp(6);
        verify(mockResultSet, times(3)).getTimestamp(7);

        assertEquals(actualList, expectedList);

    }
}
