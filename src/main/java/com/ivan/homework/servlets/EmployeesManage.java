package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.util.DBConnection;
import com.ivan.homework.dao.EmployeeDAO;
import com.ivan.homework.models.Department;
import com.ivan.homework.models.Employee;

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
public class EmployeesManage extends HttpServlet {

    private Gson gson = new Gson();
    private EmployeeDAO employeeDAO;
    PrintWriter out;

    @Override
    public void init() throws ServletException {
        try {
            DBConnection conn = new DBConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        employeeDAO = new EmployeeDAO();
        String empJsonString = null;
        try {
            empJsonString = this.gson.toJson(employeeDAO.getAllEmployee());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.println(empJsonString);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Department> departments = new ArrayList<>();
        employeeDAO = new EmployeeDAO();
        boolean wrongDepID = true;
        System.out.println("doPost");
        try {
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            int salary = Integer.parseInt(req.getParameter("salary"));
            int workExp = Integer.parseInt(req.getParameter("work_experience"));
            int departmentID = Integer.parseInt(req.getParameter("department_id"));

            for (int i = 0; i<departments.size(); i++) {
                if (departments.get(i).getId() == departmentID) {
                    Employee emp = new Employee(name, surname, workExp, salary, departmentID);
                    employeeDAO.postEmployees(emp);
                    wrongDepID = false;
                    resp.setStatus(200);
                }
            }

            if (wrongDepID) {
                resp.setStatus(404);
                throw new RuntimeException("Department id doesn't exist");
            }

//            response.sendRedirect(request.getContextPath() + "/index");
        }
        catch(Exception ex) {
//            getServletContext().getRequestDispatcher("/notfound.jsp").forward(req, resp);
            ex.printStackTrace();
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
