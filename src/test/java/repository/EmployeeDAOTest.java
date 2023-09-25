package repository;

import com.ivan.homework.dao.*;
import com.ivan.homework.models.Department;
import com.ivan.homework.models.Employee;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.util.DBConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Testcontainers
public class EmployeeDAOTest {
    private static DepartmentDAO departmentDAO;
    private static EmployeeDAO employeeDAO;
    private static HobbiesDAO hobbiesDAO;

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
        employeeDAO = new EmployeeDAOImpl(dbConnection);
        hobbiesDAO = new HobbiesDAOImpl(dbConnection);
    }

    @AfterEach
    public void clearDB() {
        try (Connection connection = DriverManager.getConnection(mySQLContainer.getJdbcUrl(), "bestuser", "bestuser")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM employees_hobbies");
            statement.executeUpdate("DELETE FROM employees");
            statement.executeUpdate("DELETE FROM hobbies");
            statement.executeUpdate("ALTER TABLE department AUTO_INCREMENT = 1");
            statement.executeUpdate("ALTER TABLE employees AUTO_INCREMENT = 1");
            statement.executeUpdate("ALTER TABLE hobbies AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));
        employeeDAO.create(new Employee("Sonya", "Blade", 5, 2000, 1));

        List<Employee> employees = employeeDAO.getAll();

        Assertions.assertEquals(2, employees.size());
    }

    @Test
    public void getByIDEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));

        Employee employee = employeeDAO.getByID(1);

        Assertions.assertEquals(1500, employee.getSalary());
    }

    @Test
    public void updateEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        Employee employee = employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));

        Employee employeeUpd = employeeDAO.update(new Employee(employee.getId(), "John", "Snow", 5, 1500, 1));

        Assertions.assertEquals("Snow", employeeUpd.getSurname());
        Assertions.assertEquals(5, employeeUpd.getWorkExp());
    }

    @Test
    public void getAllEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));

        List<Employee> employees = employeeDAO.getAll();

        Assertions.assertEquals(1, employees.size());
    }

    @Test
    public void addHobbyToEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        Hobbies hobby = hobbiesDAO.create(new Hobbies("Art"));
        Employee employee = employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));

        boolean isAdded = employeeDAO.addHobbyToEmployee(employee.getId(), hobby.getId());

        Assertions.assertTrue(isAdded);
    }

    @Test
    public void deleteEmployeeTest() {
        departmentDAO.create(new Department("IT", 123, "top-company@mail.com", 10));
        employeeDAO.create(new Employee("John", "Doe", 3, 1500, 1));
        employeeDAO.create(new Employee("Sonya", "Blade", 5, 2000, 1));

        boolean isDeleted = employeeDAO.delete(1);

        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(1, employeeDAO.getAll().size());
    }

    @AfterAll
    static void stopDb() {
        mySQLContainer.stop();
    }
}
