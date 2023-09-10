package servlets;

import com.ivan.homework.models.Department;
import com.ivan.homework.service.DepartmentServiceImpl;
import com.ivan.homework.servlets.DepartmentServlet;
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
public class DepartmentServletTest {
    @Mock
    private DepartmentServiceImpl departmentService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    private DepartmentServlet departmentServlet;
    @Mock
    private PrintWriter writer;

    @BeforeEach
    public void start() {
        StringWriter stringWriter = new StringWriter();
        departmentServlet = new DepartmentServlet();
        departmentServlet.init();
        departmentServlet.setDepartmentService(departmentService);
        writer = new PrintWriter(stringWriter);
    }

    @Test
    public void testGetAllDepartments() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn(null);

        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "IT", 111, "testDep1@mail.com", 5));
        departments.add(new Department(2, "Sales", 123, "testDep2@mail.com", 10));
        when(departmentService.getAll()).thenReturn(departments);
        when(resp.getWriter()).thenReturn(writer);

        departmentServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals(2, departments.size());
    }

    @Test
    public void testGetByIdDepartments() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");

        Department department = new Department(1, "IT", 111, "testDep1@mail.com", 5);

        when(departmentService.getByID(1)).thenReturn(department);
        when(resp.getWriter()).thenReturn(writer);

        departmentServlet.doGet(req, resp);

        verify(resp).setContentType("application/json");
        verify(resp).setCharacterEncoding("UTF-8");

        assertEquals("IT", department.getName());
        assertEquals(111, department.getPhoneNumber());
    }

    @Test
    public void testDeleteFromDepartments() throws ServletException, IOException {
        when(req.getParameter("id")).thenReturn("1");
        when(resp.getWriter()).thenReturn(writer);

        Department department = new Department(1, "IT", 111, "testDep1@mail.com", 5);
        when(departmentService.getByID(1)).thenReturn(department);
        when(departmentService.delete(1)).thenReturn(true);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

        departmentServlet.doDelete(req, resp);

        verify(resp).setStatus(200);
        assertEquals("Department with id " + department.getId()  + " is successfully deleted", stringWriter.toString());
    }
}
