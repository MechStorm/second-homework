package com.ivan.homework.dao;

import com.ivan.homework.models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private final Connection con;

    public DepartmentDAO(Connection con) {
        this.con = con;
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();

        try(Connection connection = con;
            Statement statement = con.createStatement()) {
            String sql = "SELECT * FROM department";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                department.setPhoneNumber(resultSet.getInt("phone_number"));
                department.setEmail(resultSet.getString("email"));
                department.setYearsWorks(resultSet.getInt("years_works"));

                departments.add(department);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public int postDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO department (name, phone_number, email, years_works) VALUES (?, ?, ?, ?)";
        try (Connection connection = con;
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setInt(2, department.getPhoneNumber());
            preparedStatement.setString(3, department.getEmail());
            preparedStatement.setInt(4, department.getYearsWorks());
            System.out.println(preparedStatement);
            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
