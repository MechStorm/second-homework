package com.ivan.homework.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.dao.DepartmentDAOImpl;
import com.ivan.homework.models.Department;
import com.ivan.homework.service.DepartmentService;
import com.ivan.homework.service.DepartmentServiceImpl;
import com.ivan.homework.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {
    private Gson gson;
    private DepartmentService departmentService;
    private PrintWriter writer;

    @Override
    public void init() {
        try {
            gson = new Gson();
            DBConnection conn = new DBConnection();
            DepartmentDAO departmentDAO = new DepartmentDAOImpl(conn);
            departmentService = new DepartmentServiceImpl(departmentDAO);
        } catch (SQLException e) {
            throw new RuntimeException("Error with connect to db");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            int depID = Integer.parseInt(id);
            Department department = departmentService.getByID(depID);

            if (department == null) {
                resp.setStatus(404);
            } else {
                writer = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                writer.println(gson.toJson(department));
                resp.setStatus(200);
                writer.close();
            }
        } else {
            List<Department> departments = departmentService.getAll();
            writer = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            writer.println(gson.toJson(departments));
            resp.setStatus(200);
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            writer = resp.getWriter();
//            Department department = gson.fromJson(req.getInputStream().toString(), Department.class);

            String name = req.getParameter("name");
            int phoneNumber = Integer.parseInt(req.getParameter("phone_number"));
            String email = req.getParameter("email");
            int yearsWorks = Integer.parseInt(req.getParameter("years_works"));
            Department department = new Department(name, phoneNumber, email, yearsWorks);


//            departmentService.create(department);
            writer.println(gson.toJson(departmentService.create(department)));
            resp.setStatus(201);
        } else {
            resp.setStatus(400);
        }
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
