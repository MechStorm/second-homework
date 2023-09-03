package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection con;

    public List<Employee> getAllEmployee() throws SQLException {
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
                employee.setWorkExp(resultSet.getInt("work_experience"));
                employee.setSalary(resultSet.getInt("salary"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public int postEmployees(Employee emps) {
        try {
            String sql = "INSERT INTO employees (name, surname, salary, work_experience, department_id) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, emps.getName());
                preparedStatement.setString(2, emps.getSurname());
                preparedStatement.setInt(3, emps.getSalary());
                preparedStatement.setInt(4, emps.getWorkExp());
                preparedStatement.setInt(5, emps.getDepartmentID());
                System.out.println("done");
                return preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}