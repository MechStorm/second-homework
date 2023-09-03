package com.ivan.homework.dao;

import com.ivan.homework.models.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DepartmentDAO {
    private static Connection con;

    static {
        try {
            con = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int postDepartment(Department department) {
        try {
            String sql = "INSERT INTO department (name, phone_number, email, years_works) VALUES (?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1, department.getName());
                preparedStatement.setInt(2, department.getPhoneNumber());
                preparedStatement.setString(3, department.getEmail());
                preparedStatement.setInt(4, department.getYearsWorks());
                return preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
