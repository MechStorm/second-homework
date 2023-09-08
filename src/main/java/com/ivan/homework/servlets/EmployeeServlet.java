package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.dao.EmployeeDAO;
import com.ivan.homework.dao.EmployeeDAOImpl;
import com.ivan.homework.models.Employee;
import com.ivan.homework.service.EmployeeService;
import com.ivan.homework.service.EmployeeServiceImpl;
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
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
