package com.ivan.homework.servlets;

import com.google.gson.Gson;
import com.ivan.homework.models.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/employees")
public class EmployeesManage extends HttpServlet {

    private Gson gson = new Gson();
    private List<Employee> employees;

    public void init() {
        employees = new CopyOnWriteArrayList<>();
        Employee employee1 = new Employee(1, "Steve", "Fury", "HR", 500);
        Employee employee2 = new Employee(2, "Amanda", "Johns", "Sales", 400);
        Employee employee3 = new Employee(3, "Rick", "Martin", "IT", 900);
        employees.addAll(Arrays.asList(employee1, employee2, employee3));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empJsonString = this.gson.toJson(employees);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.println(empJsonString);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!requestIsValid(req)) {
            doGet(req, resp);
        }

//        int currentID = employees.size();
//        AtomicInteger idCounter = new AtomicInteger(currentID+1);
        final String name = req.getParameter("name");
        final String surname = req.getParameter("surname");
        final String department = req.getParameter("department");
        final String salary = req.getParameter("salary");

        final Employee employee = new Employee(4, name, surname, department, Integer.parseInt(salary));

        employees.add(employee);

        doGet(req, resp);

    }

    private boolean requestIsValid(final HttpServletRequest req) {
        final String name = req.getParameter("name");
        final String surname = req.getParameter("surname");
        final String department = req.getParameter("department");
        final String salary = req.getParameter("salary");

        return name != null && name.length() > 0 &&
                surname != null && surname.length() > 0 &&
                department != null && department.length() > 0 &&
                salary != null && salary.length() > 0 &&
                salary.matches("[+]?\\d+");
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
