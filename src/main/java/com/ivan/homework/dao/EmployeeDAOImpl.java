package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final DBConnection conn;

    public EmployeeDAOImpl(DBConnection conn) {
        this.conn = conn;
    }

    @Override
    public Employee getByID(int empID) {
        Employee employee = null;
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?")) {
            preparedStatement.setInt(1, empID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setWorkExp(rs.getInt("work_experience"));
                employee.setSalary(rs.getInt("salary"));
                employee.setDepartmentID(rs.getInt("department_id"));
                List<Hobbies> hobbies = getHobbiesFromEmployees(employee.getId());
                employee.setHobbies(hobbies);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return employee;
    }

    private List<Hobbies> getHobbiesFromEmployees(int id) {
        List<Hobbies> hobbies = new ArrayList<>();
        conn.driverLink();

        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT h.id, h.name FROM hobbies h " +
                     "JOIN employees_hobbies eh ON h.id = eh.hobby_id " +
                     "JOIN employees e ON e.id = eh.employee_id WHERE e.id = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Hobbies hobby = new Hobbies();
                hobby.setId(rs.getInt("id"));
                hobby.setName(rs.getString("name"));
                hobbies.add(hobby);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }

        return hobbies;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        conn.driverLink();

        try (Connection connection = conn.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM employees");

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setWorkExp(rs.getInt("work_experience"));
                employee.setSalary(rs.getInt("salary"));
                employee.setDepartmentID(rs.getInt("department_id"));
                List<Hobbies> hobbies = getHobbiesFromEmployees(employee.getId());
                employee.setHobbies(hobbies);
                employees.add(employee);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return employees;
    }

    @Override
    public Employee create(Employee employee) {
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees(name, surname, salary, work_experience, department_id) " +
                     "VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setInt(3, employee.getSalary());
            preparedStatement.setInt(4, employee.getWorkExp());
            preparedStatement.setInt(5, employee.getDepartmentID());

            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return employee;
    }

    @Override
    public boolean addHobbyToEmployee(int empID, int hobbyID) {
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO employees_hobbies(employee_id, hobby_id) VALUES (?, ?)")) {
            preparedStatement.setInt(1, empID);
            preparedStatement.setInt(2, hobbyID);
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
    }

    @Override
    public Employee update(Employee employee) {
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET name = ?, surname = ?, salary = ?, work_experience = ?, department_id = ? WHERE id = ?")) {
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getSurname());
            preparedStatement.setInt(3, employee.getSalary());
            preparedStatement.setInt(4, employee.getWorkExp());
            preparedStatement.setInt(5, employee.getDepartmentID());
            preparedStatement.setInt(6, employee.getId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return employee;
    }

    @Override
    public boolean delete(int id) {
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement prepStatementDeleteFromLinkTable = connection.prepareStatement("DELETE FROM employees_hobbies WHERE employee_id = ?");
             PreparedStatement prepStatementDeleteEmployee = connection.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            prepStatementDeleteFromLinkTable.setInt(1, id);
            prepStatementDeleteFromLinkTable.executeUpdate();
            prepStatementDeleteEmployee.setInt(1, id);
            prepStatementDeleteEmployee.executeUpdate();
            connection.commit();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
    }
}
