package org.palax.dao.util;

import org.palax.utils.DataSourceManager;

import javax.naming.InitialContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * {@code TestDatabaseManager} class used to prepare db to test
 */
public class TestDatabaseManager {

    /**
     * Set up method which inject {@link InitialContext} to the test environment
     * also create and fill database
     */
    public static void setUpTestDatabase() {
        Connection conn = null;
        Statement stm = null;

        try {
            conn = InjectingJNDIDataSource.getConnection();
            stm = conn.createStatement();
            stm.execute("DROP DATABASE IF EXISTS `housing_service_test`");

            Path path = Paths.get(ClassLoader.getSystemResource("housing_service_test_DDL.sql").toURI());
            List<String> sqlList = Files.readAllLines(path);
            for (String sql : sqlList) {
                stm.addBatch(sql);
            }

            path = Paths.get(ClassLoader.getSystemResource("housing_service_test_DML.sql").toURI());
            sqlList = Files.readAllLines(path);
            for (String sql : sqlList) {
                stm.addBatch(sql);
            }

            stm.executeBatch();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceManager.closeAll(conn, stm, null);
        }
    }
}
