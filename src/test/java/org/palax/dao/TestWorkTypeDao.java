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
 * The {@code TestRoleDao} is a test class which used to test {@link org.palax.dao.mysql.MySQLWorkTypeDao}
 *
 * @author Taras Palashynskyy
 */

@RunWith(PowerMockRunner.class)
public class TestWorkTypeDao {

    /**Mock {@link Connection} object for test */
    @Mock
    private Connection mockConn;
    /**Mock {@link PreparedStatement} object for test */
    @Mock
    private PreparedStatement mockPreparedStm;
    /**Mock {@link ResultSet} object for test */
    @Mock
    private ResultSet mockResultSet;

    public TestWorkTypeDao() {

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
     * The method that checks whether the insertion of the {@link org.palax.entity.WorkType} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectInsertWorkType() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(1);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        WorkType workType = new WorkType();
        workType.setTypeName("work type");

        assertTrue(workTypeDao.insertWorkType(workType));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, workType.getTypeName());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong(1);

        assertEquals(workType.getWorkTypeId(), new Long(0));

    }

    /**
     * The method that checks whether the incorrect insertion of the {@link org.palax.entity.WorkType} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testIncorrectInsertWorkType() throws SQLException {

        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStm);
        when(mockPreparedStm.executeUpdate()).thenReturn(0);
        when(mockPreparedStm.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        WorkType workType = new WorkType();
        workType.setTypeName("work type");

        assertFalse(workTypeDao.insertWorkType(workType));

        verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
        verify(mockPreparedStm, times(1)).setString(1, workType.getTypeName());
        verify(mockPreparedStm, times(1)).executeUpdate();
        verify(mockResultSet, times(0)).next();
        verify(mockResultSet, times(0)).getLong(1);

        assertEquals(workType.getWorkTypeId(), null);

    }

    /**
     * The method that checks successfuly gets all {@link WorkType} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testCorrectGetAllWorkType() throws SQLException {

        List<WorkType> expectedList = new ArrayList<>();

        for(long i = 1; i < 4; i++) {
            expectedList.add(DataGenerator.generateWorkType(i));
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
                return expectedList.get(i++).getWorkTypeId();
            }
        });
        when(mockResultSet.getString(2)).thenAnswer(new Answer<String>() {
            int i = 0;

            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return expectedList.get(i++).getTypeName();
            }
        });

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        List<WorkType> actualList = workTypeDao.getAllWorkType();

        verify(mockConn, times(1)).prepareStatement(anyString());
        verify(mockPreparedStm, times(1)).executeQuery();
        verify(mockResultSet, times(4)).next();
        verify(mockResultSet, times(3)).getLong(1);
        verify(mockResultSet, times(3)).getString(2);

        assertEquals(actualList, expectedList);

    }

    /**
     * The method that checks successfully gets all {@link WorkType} by {@code name} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetWorkTypeByName() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        WorkType expectedWorkType = new WorkType();
        expectedWorkType.setWorkTypeId(5L);
        expectedWorkType.setTypeName("work_type5");

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        WorkType actualWorkType = workTypeDao.getWorkTypeByName("work_type5");

        assertEquals(expectedWorkType, actualWorkType);
    }

    /**
     * The method that checks successfully gets all {@link WorkType} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetWorkTypeById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        WorkType expectedWorkType = new WorkType();
        expectedWorkType.setWorkTypeId(5L);
        expectedWorkType.setTypeName("work_type5");

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        WorkType actualWorkType = workTypeDao.getWorkTypeById(5L);

        assertEquals(expectedWorkType, actualWorkType);
    }

    /**
     * The method that checks successfully update {@link WorkType} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testUpdateWorkType() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        WorkType expectedWorkType = new WorkType();
        expectedWorkType.setWorkTypeId(4L);
        expectedWorkType.setTypeName("update");

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        assertTrue(workTypeDao.updateWorkType(expectedWorkType));

        WorkType actualWorkType = workTypeDao.getWorkTypeById(4L);

        assertEquals(expectedWorkType, actualWorkType);
    }

    /**
     * The method that checks successfully delete {@link WorkType} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testDeleteWorkType() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return con;
                });

        WorkType expectedWorkType = new WorkType();
        expectedWorkType.setWorkTypeId(5L);
        expectedWorkType.setTypeName("work_type5");

        WorkTypeDao workTypeDao = MySQLDAOFactory.getWorkTypeDao();

        assertTrue(workTypeDao.deleteWorkType(expectedWorkType));

        assertNull(workTypeDao.getWorkTypeById(5L));
    }
}
