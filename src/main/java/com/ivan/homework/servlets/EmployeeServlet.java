package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.dao.EmployeeDAO;
import com.ivan.homework.dao.EmployeeDAOImpl;
import com.ivan.homework.models.Department;
import com.ivan.homework.models.Employee;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.service.EmployeeService;
import com.ivan.homework.service.EmployeeServiceImpl;
import com.ivan.homework.service.HobbiesService;
import com.ivan.homework.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private Gson gson;
    private EmployeeService employeeService;
    private HobbiesService hobbiesService;
    private PrintWriter writer;

    @Override
    public void init() {
        try {
            gson = new Gson();
            DBConnection conn = new DBConnection();
            EmployeeDAO employeeDAO = new EmployeeDAOImpl(conn);
            employeeService = new EmployeeServiceImpl(employeeDAO);
        } catch (SQLException e) {
            throw new RuntimeException("Error with connect to db");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            int empID = Integer.parseInt(id);
            Employee employee = employeeService.getByID(empID);

            if (employee == null) {
                resp.setStatus(404);
            } else {
                writer = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                writer.println(gson.toJson(employee));
                resp.setStatus(200);
                writer.close();
            }
        } else {
            List<Employee> employees = employeeService.getAll();
            writer = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            writer.println(gson.toJson(employees));
            resp.setStatus(200);
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            writer = resp.getWriter();

            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            int salary = Integer.parseInt(req.getParameter("salary"));
            int workExp = Integer.parseInt(req.getParameter("work_experience"));
            int depID = Integer.parseInt(req.getParameter("department_id"));
            Employee employee = new Employee(name, surname, workExp, salary, depID);

            writer.println(gson.toJson(employeeService.create(employee)));
        } else {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        String idEmp = req.getParameter("empId");
        String idHob = req.getParameter("hobbyId");

        if (idEmp != null && idHob != null) {
            int empID = Integer.parseInt(idEmp);
            int hobbyID = Integer.parseInt(idHob);

            Employee employee = employeeService.getByID(empID);
            Hobbies hobby = hobbiesService.getByID(hobbyID);

            if (employee == null || hobby == null) {
                resp.setStatus(404);
            } else {
                employeeService.addHobbytoEmployee(empID, hobbyID);
                resp.setStatus(200);
            }
        }

        if (path == null) {
            writer = resp.getWriter();

            int empID = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            int salary = Integer.parseInt(req.getParameter("salary"));
            int workExp = Integer.parseInt(req.getParameter("work_experience"));
            int depID = Integer.parseInt(req.getParameter("department_id"));

            if (empID == 0 || name == null || surname == null || salary == 0 || workExp == 0 || depID == 0) {
                resp.setStatus(400);
                return;
            }

            Employee employee = new Employee(empID, name, surname, salary, workExp, depID);
            writer.println(gson.toJson(employeeService.update(employee)));
            resp.setStatus(200);
        } else {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            Employee emp = employeeService.getByID(id);
            if (emp == null) {
                resp.setStatus(404);
            } else {
                boolean deleteSuccess = employeeService.delete(id);
                if (deleteSuccess) {
                    writer.println("Employee with id " + id + "is successfully deleted");
                    resp.setStatus(200);
                } else {
                    resp.setStatus(404);
                }
            }
        }
    }
}
