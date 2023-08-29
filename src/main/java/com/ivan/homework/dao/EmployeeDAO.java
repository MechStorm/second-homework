package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//Create the ability to fill the database yourself

public class EmployeeDAO {

    private static Connection con;

    static {
        try {
            con = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAllEmployee() {
        List<Employee> employees = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM employees";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setDepartment(resultSet.getString("department"));
                employee.setSalary(resultSet.getInt("salary"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(employees);

        return employees;
    }
}