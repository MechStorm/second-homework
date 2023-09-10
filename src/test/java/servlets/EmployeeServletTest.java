package servlets;

import com.ivan.homework.models.Employee;
import com.ivan.homework.service.EmployeeServiceImpl;
import com.ivan.homework.servlets.EmployeeServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServletTest {
    @Mock
    private EmployeeServiceImpl employeeService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    private EmployeeServlet employeeServlet;
    @Mock
    PrintWriter writer;

    @BeforeEach
    public void start() {
        StringWriter stringWriter = new StringWriter();
        employeeServlet = new EmployeeServlet();
        employeeServlet.init();
        employeeServlet.setEmployeeService(employeeService);
        writer = new PrintWriter(stringWriter);
    }

    @Test
    public void testGetAllEmployees() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn(null);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "John", "Smith", 5, 1000, 2));
        employees.add(new Employee(2, "Tom", "Huddleston", 3, 800, 1));
        when(employeeService.getAll()).thenReturn(employees);
        when(resp.getWriter()).thenReturn(writer);

        employeeServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals(2, employees.size());
    }

    @Test
    public void testGetByIdEmployees() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");

        Employee employee = new Employee(1, "John", "Smith", 5, 1000, 2);

        when(employeeService.getByID(1)).thenReturn(employee);
        when(resp.getWriter()).thenReturn(writer);

        employeeServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals(1, employee.getId());
        assertEquals(1000, employee.getSalary());
        assertEquals(2, employee.getDepartmentID());
        assertEquals("John", employee.getName());
        assertEquals("Smith", employee.getSurname());
        assertEquals(5, employee.getWorkExp());
    }

    @Test
    public void testDeleteFromEmployees() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");
        when(resp.getWriter()).thenReturn(writer);

        Employee employee = new Employee(1, "John", "Smith", 5, 1000, 2);
        when(employeeService.getByID(1)).thenReturn(employee);
        when(employeeService.delete(1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

        employeeServlet.doDelete(req, resp);

        verify(resp).setStatus(200);
        assertEquals("Employee with id " + employee.getId()  + " is successfully deleted", stringWriter.toString());
    }
}
