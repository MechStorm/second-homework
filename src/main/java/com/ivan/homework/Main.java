package com.ivan.homework;

import com.ivan.homework.dao.DBConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        System.out.println(connection.getConnection());
    }
}