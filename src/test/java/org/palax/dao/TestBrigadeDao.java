package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.util.*;
import org.palax.entity.Brigade;
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
 * The {@code TestBrigadeDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLBrigadeDao}
 *
 * @author Taras Palashynskyy
 */
@PrepareForTest({DataSourceManager.class})
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

    /**
     * Set up method which inject {@link InitialContext} to the test environment
     * alse create and fill database
     */
    @BeforeClass
    public static void setUpClass() {
        TestDatabaseManager.setUpTestDatabase();
    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.Brigade} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testCorrectInsertBrigade() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        Brigade brigade = DataGenerator.generateBrigade(1);

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
     */
    @Test
    public void testIncorrectInsertBrigade() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        Brigade brigade = DataGenerator.generateBrigade(1);
        brigade.setBrigadeId(null);

        assertFalse(brigadeDao.insertBrigade(brigade));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, brigade.getBrigadeName());
        verify(mockPreparedStm, times(1)).setLong(2, brigade.getWorkType().getWorkTypeId());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertNull(brigade.getBrigadeId());

    }

    /**
     * The method that checks successfully gets all {@link Brigade} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testCorrectGetAllBrigade() throws SQLException {

        List<Brigade> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateBrigade(i));
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
                return expectedList.get(j++ % 2 == 0 ? i : i++).getBrigadeId();
            }
        });
        when(mockResultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getBrigadeName();
            }
        });
        when(mockResultSet.getLong(3)).thenAnswer(new Answer<Long>() {
            int i = 0;
            int j = 0;

            @Override
            public Long answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(j++ % 2 == 0 ? i : i++).getWorkType().getWorkTypeId();
            }
        });
        when(mockResultSet.getString(4)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getWorkType().getTypeName();
            }
        });

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        List<Brigade> actualList = brigadeDao.getAllBrigade();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(6)).getLong(1);
        verify(mockResultSet, times(3)).getString(2);
        verify(mockResultSet, times(6)).getLong(3);
        verify(mockResultSet, times(3)).getString(4);

        assertEquals(actualList, expectedList);
    }

    /**
     * The method that checks successfully gets all {@link Brigade} by {@code workType} name from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetAllBrigadeByWorkType() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                   Connection con = InjectingJNDIDataSource.getConnection();
                   con.setCatalog("housing_service_test");
                    return con;
                });

        Builder<WorkType> workTypeBuilder = WorkTypeBuilder.getBuilder().constructWorkType(1L);

        List<Brigade> expectedList = new ArrayList<>();
        Brigade brigade = BrigadeBuilder.getBuilder().constructBrigade(1L, workTypeBuilder).build();
        expectedList.add(brigade);

        brigade = BrigadeBuilder.getBuilder().constructBrigade(2L, workTypeBuilder).build();
        expectedList.add(brigade);

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        List<Brigade> actualList = brigadeDao.getAllBrigadeByWorkType("work_type1");

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully gets all {@link Brigade} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testGetBrigadeById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Brigade expectedBrigade = new Brigade();
        expectedBrigade.setBrigadeId(1L);
        expectedBrigade.setBrigadeName("brigade1");
        WorkType workType= new WorkType();
        expectedBrigade.setWorkType(workType);
        expectedBrigade.getWorkType().setWorkTypeId(1L);
        expectedBrigade.getWorkType().setTypeName("work_type1");

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        Brigade actualBrigade = brigadeDao.getBrigadeById(1L);

        assertEquals(expectedBrigade, actualBrigade);
    }

    /**
     * The method that checks successfully update {@link Brigade} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testUpdateBrigade() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Brigade expectedBrigade = new Brigade();
        expectedBrigade.setBrigadeId(7L);
        expectedBrigade.setBrigadeName("update");
        WorkType workType= new WorkType();
        expectedBrigade.setWorkType(workType);
        expectedBrigade.getWorkType().setWorkTypeId(2L);
        expectedBrigade.getWorkType().setTypeName("work_type2");

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        assertTrue(brigadeDao.updateBrigade(expectedBrigade));

        Brigade actualBrigade = brigadeDao.getBrigadeById(7L);

        assertEquals(expectedBrigade, actualBrigade);
    }

    /**
     * The method that checks successfully delete {@link Brigade} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @Test
    public void testDeleteBrigade() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        Brigade brigade = new Brigade();
        brigade.setBrigadeId(6L);
        brigade.setBrigadeName("brigade6");
        WorkType workType= new WorkType();
        brigade.setWorkType(workType);
        brigade.getWorkType().setWorkTypeId(4L);
        brigade.getWorkType().setTypeName("work_type4");

        BrigadeDao brigadeDao = MySQLDAOFactory.getBrigadeDao();

        assertTrue(brigadeDao.deleteBrigade(brigade));

        assertNull(brigadeDao.getBrigadeById(6L));
    }


}
