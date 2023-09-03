package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.dao.DepartmentDAO;
import com.ivan.homework.models.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/departments")
public class DepartmentManage extends HttpServlet {
    private DepartmentDAO departmentDAO;
    private Gson gson = new Gson();
    private PrintWriter out;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        departmentDAO = new DepartmentDAO();
        try {
            String name = req.getParameter("name");
            int phoneNumber = Integer.parseInt(req.getParameter("phone_number"));
            String email = req.getParameter("email");
            int yearsWorks = Integer.parseInt(req.getParameter("years_works"));

            Department department = new Department(name, phoneNumber, email, yearsWorks);
            departmentDAO.postDepartment(department);
            resp.setStatus(200);

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
