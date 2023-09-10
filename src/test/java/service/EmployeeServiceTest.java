package service;

import com.ivan.homework.dao.EmployeeDAO;
import com.ivan.homework.models.Employee;
import com.ivan.homework.service.EmployeeService;
import com.ivan.homework.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    EmployeeService employeeService;
    @Mock
    EmployeeDAO employeeDAO;

    @BeforeEach
    public void start() {
        employeeService = new EmployeeServiceImpl(employeeDAO);
    }

    @Test
    public void testGetAllEmployee() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John", "Smith", 5, 1000, 2));
        employees.add(new Employee(2, "Tom", "Huddleston", 3, 800, 1));

        when(employeeDAO.getAll()).thenReturn(employees);

        assertEquals(employees, employeeService.getAll());
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setId(1);
        employee.setSurname("Smith");
        employee.setSalary(500);
        employee.setWorkExp(5);
        employee.setDepartmentID(1);

        when(employeeDAO.getByID(employee.getId())).thenReturn(employee);

        assertEquals(employee, employeeService.getByID(employee.getId()));
    }

    @Test
    public void testCreateNewEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setId(1);
        employee.setSurname("Smith");
        employee.setSalary(500);
        employee.setWorkExp(5);
        employee.setDepartmentID(1);

        when(employeeDAO.create(employee)).thenReturn(employee);

        assertEquals(employee, employeeService.create(employee));
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setId(1);
        employee.setSurname("Smith");
        employee.setSalary(500);
        employee.setWorkExp(5);
        employee.setDepartmentID(1);

        when(employeeDAO.update(employee)).thenReturn(employee);

        assertEquals(employee, employeeService.update(employee));
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setId(1);
        employee.setSurname("Smith");
        employee.setSalary(500);
        employee.setWorkExp(5);
        employee.setDepartmentID(1);

        when(employeeDAO.delete(employee.getId())).thenReturn(true);

        assertTrue(employeeService.delete(employee.getId()));
    }
}
