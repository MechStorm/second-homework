package repository;


import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.dao.DepartmentDAOImpl;
import com.ivan.homework.models.Department;
import com.ivan.homework.util.DBConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    static void startDb() {
        mySQLContainer.start();
    }

    @BeforeEach
    public void createTables() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        dbConnection.setUrl(mySQLContainer.getJdbcUrl());
        departmentDAO = new DepartmentDAOImpl(dbConnection);
    }

    @AfterEach
    public void clearDB() {
        try (Connection connection = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), "bestuser", "bestuser")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM department");
            statement.executeUpdate("ALTER TABLE department AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createDepartmentTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));

        List<Department> departments = departmentDAO.getAll();

        assertEquals(1, departments.size());
    }

    @Test
    public void getDepartmentByIDTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        Department department = departmentDAO.getByID(1);

        assertEquals("IT", department.getName());
        assertNotNull(department);
        assertEquals(1, departmentDAO.getAll().size());
    }

    @Test
    public void updateDepartment() {
        Department department = departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        department = departmentDAO.update(new Department(department.getId(), "IT", 456, "top-company@mail.com", 10));

        assertEquals(456, department.getPhoneNumber());
    }

    @Test
    public void getAllDepartmentsTest() {
        Department dep1 = departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        Department dep2 = departmentDAO.create(new Department("Sales", 456, "top-company2@mail.com", 5));

        List<Department> departments = new ArrayList<>();
        departments.add(dep1);
        departments.add(dep2);

        assertEquals("Sales", dep2.getName());
        assertEquals(10, dep1.getYearsWorks());
        assertEquals(2, departmentDAO.getAll().size());
    }

    @Test
    public void deleteDepartmentsTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        departmentDAO.create(new Department("Sales", 456, "top-company2@mail.com", 5));

        boolean deleted = departmentDAO.delete(2);

        assertTrue(deleted);
        assertEquals(1, departmentDAO.getAll().size());
    }

    @AfterAll
    static void stopDb() {
        mySQLContainer.stop();
    }
}
