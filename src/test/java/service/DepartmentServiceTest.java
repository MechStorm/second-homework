package service;

import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.models.Department;
import com.ivan.homework.service.DepartmentService;
import com.ivan.homework.service.DepartmentServiceImpl;
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
public class DepartmentServiceTest {
    DepartmentService departmentService;
    @Mock
    DepartmentDAO departmentDAO;

    @BeforeEach
    public void start() {
        departmentService = new DepartmentServiceImpl(departmentDAO);
    }

    @Test
    public void testGetAllDepartment() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "IT", 111, "testDep1@mail.com", 5));
        departments.add(new Department(2, "Sales", 123, "testDep2@mail.com", 10));

        when(departmentDAO.getAll()).thenReturn(departments);

        assertEquals(departments, departmentService.getAll());
    }

    @Test
    public void testGetDepartmentById() {
        Department department = new Department();
        department.setName("IT");
        department.setId(1);
        department.setEmail("some-company@mail.com");
        department.setPhoneNumber(123);
        department.setYearsWorks(5);

        when(departmentDAO.getByID(department.getId())).thenReturn(department);

        assertEquals(department, departmentService.getByID(department.getId()));
    }

    @Test
    public void testCreateNewDepartment() {
        Department department = new Department();
        department.setName("IT");
        department.setId(1);
        department.setEmail("some-company@mail.com");
        department.setPhoneNumber(123);
        department.setYearsWorks(5);

        when(departmentDAO.create(department)).thenReturn(department);

        assertEquals(department, departmentService.create(department));
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department();
        department.setName("IT");
        department.setId(1);
        department.setEmail("some-company@mail.com");
        department.setPhoneNumber(123);
        department.setYearsWorks(5);

        when(departmentDAO.update(department)).thenReturn(department);

        assertEquals(department, departmentService.update(department));
    }

    @Test
    public void testDeleteDepartment() {
        Department department = new Department();
        department.setName("IT");
        department.setId(1);
        department.setEmail("some-company@mail.com");
        department.setPhoneNumber(123);
        department.setYearsWorks(5);

        when(departmentDAO.delete(department.getId())).thenReturn(true);

        assertTrue(departmentService.delete(department.getId()));
    }

}
