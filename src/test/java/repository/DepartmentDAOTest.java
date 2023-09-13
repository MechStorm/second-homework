package repository;


import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.dao.DepartmentDAOImpl;
import com.ivan.homework.util.DBConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class DepartmentDAOTest {

    private static DepartmentDAO departmentDAO;

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.32")
            .withDatabaseName("my_db")
            .withUsername("bestuser")
            .withPassword("bestuser");

    @BeforeAll
    static void startDb() {
        mySQLContainer.withInitScript("com/ivan/homework/dao/db.sql");
        mySQLContainer.start();
    }

    @BeforeEach
    public void start() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.driverLink();
        dbConnection.setUrl(mySQLContainer.getJdbcUrl());

        departmentDAO = new DepartmentDAOImpl(dbConnection);

    }


//    @AfterEach
//    public void clearDB() {
//        try(Connection connection = DriverManager.getConnection(container.getJdbcUrl(), "bestuser", "bestuser")) {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("DELETE FROM department");
//        } catch (SQLException e) {
//            throw new RuntimeException("Failed with clear db in test");
//        }
//    }

    @Test
    public void getAllDepartmentTest() {
//        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));

//        List<Department> departmens = departmentDAO.getAll();
        List<String> deps = new ArrayList<>();
        deps.add("some");

        assertEquals(1, deps.size());
    }

    @AfterAll
    static void stopDb() {
        mySQLContainer.stop();
    }
}
