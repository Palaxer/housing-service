package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.mysql.MySQLBidDao;
import org.palax.dao.mysql.MySQLBrigadeDao;
import org.palax.dao.mysql.MySQLUserDao;
import org.palax.dao.util.DataGenerator;
import org.palax.dao.util.InjectingJNDIDataSource;
import org.palax.dao.util.TestDatabaseManager;
import org.palax.entity.*;
import org.palax.utils.DataSourceManager;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.naming.InitialContext;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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
     * Set up method which inject {@link InitialContext} to the test environment
     * alse create and fill database
     */
    @BeforeClass
    public static void setUpClass() {
        TestDatabaseManager.setUpTestDatabase();
    }

    /**
     * The method that checks whether the insertion of the {@link org.palax.entity.WorkPlane} successfully in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
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

    /**
     * The method that checks successfully gets all {@link WorkPlane} by {@code workType} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetAllWorkPlaneByWorkType() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        List<WorkPlane> expectedList = new ArrayList<>();
        WorkPlane workPlane = new WorkPlane();
        workPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        workPlane.setUserAdvisor(user);
        workPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        workPlane.setBid(bid);
        workPlane.setStatus("COMPLETE");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedList.add(workPlane);

        workPlane = new WorkPlane();
        workPlane.setWorkPlaneId(2L);
        user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        workPlane.setUserAdvisor(user);
        workPlane.setBrigade(brigade);
        bid = new Bid();
        bid.setBidId(1L);
        bid.setWorkType(workType);
        bid.setWorkScope(1L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(1L);
        bid.getUserTenant().setLogin("login1");
        bid.getUserTenant().setPassword("passwd1");
        bid.getUserTenant().setRole(new Role());
        bid.getUserTenant().getRole().setRoleId(1L);
        bid.getUserTenant().getRole().setRoleType("role1");
        bid.getUserTenant().setFirstName("first_name1");
        bid.getUserTenant().setLastName("last_name1");
        bid.getUserTenant().setBrigade(new Brigade());
        bid.getUserTenant().getBrigade().setWorkType(new WorkType());
        bid.getUserTenant().setStreet("str1");
        bid.getUserTenant().setHouseNumber("1");
        bid.getUserTenant().setApartment(1L);
        bid.getUserTenant().setCity("city1");
        bid.getUserTenant().setPhoneNumber("093-722-9393");
        bid.setStatus("NEW");
        bid.setDescription("desc1");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        workPlane.setBid(bid);
        workPlane.setStatus("NEW");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedList.add(workPlane);


        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        List<WorkPlane> actualList = workPlaneDao.getAllWorkPlaneByWorkType("work_type1");

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully gets all {@link WorkPlane} by {@code brigadeId} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetAllWorkPlaneByBrigadeId() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        List<WorkPlane> expectedList = new ArrayList<>();
        WorkPlane workPlane = new WorkPlane();
        workPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        workPlane.setUserAdvisor(user);
        workPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        workPlane.setBid(bid);
        workPlane.setStatus("COMPLETE");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedList.add(workPlane);

        workPlane = new WorkPlane();
        workPlane.setWorkPlaneId(2L);
        user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        workPlane.setUserAdvisor(user);
        workPlane.setBrigade(brigade);
        bid = new Bid();
        bid.setBidId(1L);
        bid.setWorkType(workType);
        bid.setWorkScope(1L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(1L);
        bid.getUserTenant().setLogin("login1");
        bid.getUserTenant().setPassword("passwd1");
        bid.getUserTenant().setRole(new Role());
        bid.getUserTenant().getRole().setRoleId(1L);
        bid.getUserTenant().getRole().setRoleType("role1");
        bid.getUserTenant().setFirstName("first_name1");
        bid.getUserTenant().setLastName("last_name1");
        bid.getUserTenant().setBrigade(new Brigade());
        bid.getUserTenant().getBrigade().setWorkType(new WorkType());
        bid.getUserTenant().setStreet("str1");
        bid.getUserTenant().setHouseNumber("1");
        bid.getUserTenant().setApartment(1L);
        bid.getUserTenant().setCity("city1");
        bid.getUserTenant().setPhoneNumber("093-722-9393");
        bid.setStatus("NEW");
        bid.setDescription("desc1");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        workPlane.setBid(bid);
        workPlane.setStatus("NEW");
        workPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        workPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedList.add(workPlane);


        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        List<WorkPlane> actualList = workPlaneDao.getAllWorkPlaneByBrigadeId(1L);

        assertEquals(expectedList, actualList);
    }

    /**
     * The method that checks successfully gets all {@link WorkPlane} by {@code id} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetWorkPlaneById() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkPlane expectedWorkPlane = new WorkPlane();
        expectedWorkPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        expectedWorkPlane.setUserAdvisor(user);
        expectedWorkPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        expectedWorkPlane.setBid(bid);
        expectedWorkPlane.setStatus("COMPLETE");
        expectedWorkPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedWorkPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        WorkPlane actualWorkPlane = workPlaneDao.getWorkPlaneById(1L);

        assertEquals(expectedWorkPlane, actualWorkPlane);
    }

    /**
     * The method that checks successfully gets all {@link WorkPlane} by {@code bidId} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testGetWorkPlaneByBidId() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkPlane expectedWorkPlane = new WorkPlane();
        expectedWorkPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        expectedWorkPlane.setUserAdvisor(user);
        expectedWorkPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        expectedWorkPlane.setBid(bid);
        expectedWorkPlane.setStatus("COMPLETE");
        expectedWorkPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedWorkPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        WorkPlane actualWorkPlane = workPlaneDao.getWorkPlaneByBidId(3L);

        assertEquals(expectedWorkPlane, actualWorkPlane);
    }

    /**
     * The method that checks successfully delete {@link WorkPlane} from the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testDeleteWorkPlane() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkPlane expectedWorkPlane = new WorkPlane();
        expectedWorkPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        expectedWorkPlane.setUserAdvisor(user);
        expectedWorkPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        expectedWorkPlane.setBid(bid);
        expectedWorkPlane.setStatus("UPDATE");
        expectedWorkPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedWorkPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        assertTrue(workPlaneDao.deleteWorkPlane(expectedWorkPlane));

        assertNull(workPlaneDao.getWorkPlaneById(1L));
    }

    /**
     * The method that checks successfully update {@link WorkPlane} in the DB
     * In this case we use {@link PowerMockito} for replacement behavior
     * with utility static method {@code DataSourceManager.getConnection()} because
     * there is no opportunity to use the DI
     */
    @PrepareForTest({DataSourceManager.class})
    @Test
    public void testUpdateWorkPlane() {
        PowerMockito.mockStatic(DataSourceManager.class);

        PowerMockito.when(DataSourceManager.getConnection())
                .thenAnswer(invocationOnMock -> {
                    Connection con = InjectingJNDIDataSource.getConnection();
                    con.setCatalog("housing_service_test");
                    return  con;
                });

        WorkPlane expectedWorkPlane = new WorkPlane();
        expectedWorkPlane.setWorkPlaneId(1L);
        User user = new User();
        user.setUserId(4L);
        user.setLogin("login4");
        user.setPassword("passwd4");
        Role role = new Role();
        user.setRole(role);
        user.getRole().setRoleId(2L);
        user.getRole().setRoleType("role2");
        user.setFirstName("first_name4");
        user.setLastName("last_name4");
        user.setPosition("pos2");
        Brigade brigade = new Brigade();
        user.setBrigade(brigade);
        user.getBrigade().setBrigadeId(1L);
        user.getBrigade().setBrigadeName("brigade1");
        WorkType workType = new WorkType();
        user.getBrigade().setWorkType(workType);
        user.getBrigade().getWorkType().setWorkTypeId(1L);
        user.getBrigade().getWorkType().setTypeName("work_type1");
        user.setStreet("str4");
        user.setHouseNumber("4");
        user.setApartment(4L);
        user.setCity("city1");
        user.setPhoneNumber("093-722-9395");
        expectedWorkPlane.setUserAdvisor(user);
        expectedWorkPlane.setBrigade(brigade);
        Bid bid = new Bid();
        bid.setBidId(3L);
        bid.setWorkType(workType);
        bid.setWorkScope(3L);
        bid.setLeadTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        bid.setUserTenant(new User());
        bid.getUserTenant().setUserId(3L);
        bid.getUserTenant().setLogin("login3");
        bid.getUserTenant().setPassword("passwd3");
        bid.getUserTenant().setRole(role);
        bid.getUserTenant().setFirstName("first_name3");
        bid.getUserTenant().setLastName("last_name3");
        bid.getUserTenant().setPosition("pos1");
        bid.getUserTenant().setBrigade(brigade);
        bid.getUserTenant().setStreet("str3");
        bid.getUserTenant().setHouseNumber("3");
        bid.getUserTenant().setApartment(3L);
        bid.getUserTenant().setCity("city2");
        bid.getUserTenant().setPhoneNumber("093-722-9394");
        bid.setStatus("COMPLETE");
        bid.setDescription("desc3");
        bid.setBidTime(Timestamp.valueOf(LocalDateTime.of(2016,12,12,0,0,0)));
        expectedWorkPlane.setBid(bid);
        expectedWorkPlane.setStatus("UPDATE");
        expectedWorkPlane.setWorkTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));
        expectedWorkPlane.setCompleteTime(Timestamp.valueOf(LocalDateTime.of(2016, 12, 12, 0, 0, 0)));

        WorkPlaneDao workPlaneDao = MySQLDAOFactory.getWorkPlaneDao();

        assertTrue(workPlaneDao.updateWorkPlane(expectedWorkPlane));

        WorkPlane actualWorkPlane = workPlaneDao.getWorkPlaneById(1L);

        assertEquals(expectedWorkPlane, actualWorkPlane);
    }
}
