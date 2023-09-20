package repository;


import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.dao.DepartmentDAOImpl;
import com.ivan.homework.models.Department;
import com.ivan.homework.util.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class DepartmentDAOTest {

    private static DepartmentDAO departmentDAO;

    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7.34")
            .withDatabaseName("my_db")
            .withUsername("bestuser")
            .withPassword("bestuser")
            .withInitScript("db.sql");

    @BeforeAll
    static void startDb() throws SQLException {
        mySQLContainer.start();
        DBConnection dbConnection = new DBConnection();
        dbConnection.driverLink();
        dbConnection.setUrl(mySQLContainer.getJdbcUrl());
        departmentDAO = new DepartmentDAOImpl(dbConnection);
    }

    @AfterEach
    public void clearDB() {
        try(Connection connection = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), "bestuser", "bestuser")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM department");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllDepartmentTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));

        List<Department> departments = departmentDAO.getAll();

        assertEquals(1, departments.size());
    }

    @AfterAll
    static void stopDb() {
        mySQLContainer.stop();
    }
}
