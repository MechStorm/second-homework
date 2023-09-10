package com.ivan.homework.dao;

import com.ivan.homework.models.Employee;
import com.ivan.homework.models.Hobbies;
import com.ivan.homework.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HobbiesDAOImpl implements HobbiesDAO {
    private final DBConnection conn;

    public HobbiesDAOImpl(DBConnection conn) {
        this.conn = conn;
    }

    @Override
    public Hobbies getByID(int hobbyID) {
        Hobbies hobby = null;
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hobbies WHERE ID = ?")) {

            preparedStatement.setInt(1, hobbyID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                hobby = new Hobbies();
                hobby.setId(rs.getInt("id"));
                hobby.setName(rs.getString("name"));
                List<Employee> employees = getEmployeesByHobbies(hobby.getId());
                hobby.setEmployees(employees);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return hobby;
    }

    private List<Employee> getEmployeesByHobbies(int id) {
        List<Employee> employees = new ArrayList<>();
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.id, e.name, e.surname, e.salary, e.work_experience, e.department_id " +
                     "FROM employees e JOIN employees_hobbies eh ON e.id = eh.employee_id " +
                     "JOIN hobbies h ON h.id = eh.hobby_id WHERE h.id = ?")) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("surname"));
                employee.setSalary(rs.getInt("salary"));
                employee.setWorkExp(rs.getInt("work_experience"));
                employee.setDepartmentID(rs.getInt("department_id"));
                employees.add(employee);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return employees;
    }

    @Override
    public List<Hobbies> getAll() {
        List<Hobbies> hobbies = new ArrayList<>();
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM hobbies");


            while (rs.next()) {
                Hobbies hobby = new Hobbies();
                hobby.setId(rs.getInt("id"));
                hobby.setName(rs.getString("name"));
                List<Employee> employees = getEmployeesByHobbies(hobby.getId());
                hobby.setEmployees(employees);
                hobbies.add(hobby);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return hobbies;
    }

    @Override
    public Hobbies create(Hobbies hobby) {
        conn.driverLink();
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO hobbies(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, hobby.getName());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return hobby;
    }

    @Override
    public Hobbies update(Hobbies hobby) {
        conn.driverLink();
        try(Connection connection = conn.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hobbies SET name = ? WHERE id = ?")) {
            preparedStatement.setString(1, hobby.getName());
            preparedStatement.setInt(2, hobby.getId());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
        return hobby;
    }

    @Override
    public boolean delete(int id) {
        conn.driverLink();
        try(Connection connection = conn.getConnection();
        PreparedStatement prepStatementDeleteFromLinkTable = connection.prepareStatement("DELETE FROM employees_hobbies WHERE hobby_id = ?");
            PreparedStatement prepStatementDeleteHobby = connection.prepareStatement("DELETE FROM hobbies WHERE id = ?")) {

            prepStatementDeleteFromLinkTable.setInt(1, id);
            prepStatementDeleteFromLinkTable.executeUpdate();
            prepStatementDeleteHobby.setInt(1, id);
            prepStatementDeleteHobby.executeUpdate();

            connection.commit();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error with db connection");
        }
    }
}
