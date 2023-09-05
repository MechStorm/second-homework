package com.ivan.homework.dao;

import com.ivan.homework.models.Department;
import com.ivan.homework.models.Employee;
import com.ivan.homework.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO{
    private final DBConnection conn;
    public DepartmentDAOImpl(DBConnection conn) {
        this.conn = conn;
    }

    @Override
    public Department getByID(int depID) {
        Department department = null;
        conn.driverLink();
        try(Connection connection = conn.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM department WHERE id = ?")){
            preparedStatement.setInt(1, depID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                department.setPhoneNumber(rs.getInt("phone_number"));
                department.setEmail(rs.getString("email"));
                department.setYearsWorks(rs.getInt("years_works"));
                List<Employee> employees = getEmployeesFromDepartment(department.getId());
                department.setEmployees(employees);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }

        return department;
    }

    private List<Employee> getEmployeesFromDepartment(int depID) {
        List<Employee> employees = new ArrayList<>();

        conn.driverLink();
        try(Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.id, e.name, e.surname, e.salary, e.work_experience" +
                " FROM employees e JOIN department d ON d.id = e.department_id WHERE d.id = ?")){
            preparedStatement.setInt(1, depID);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setSurname(rs.getString("surname"));
                emp.setSalary(rs.getInt("salary"));
                emp.setWorkExp(rs.getInt("work_experience"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with SQL query or incorrect db connection");
        }
        return employees;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();

        conn.driverLink();
        try(Connection connection = conn.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM department");

            while(rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                department.setPhoneNumber(rs.getInt("phone_number"));
                department.setEmail(rs.getString("email"));
                department.setYearsWorks(rs.getInt("years_works"));
                List<Employee> employees = getEmployeesFromDepartment(department.getId());
                department.setEmployees(employees);
                departments.add(department);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error with SQL query or incorrect db connection");
        }
        return departments;
    }
}
