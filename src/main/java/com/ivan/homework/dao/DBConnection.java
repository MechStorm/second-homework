package com.ivan.homework.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
        Properties p = new Properties();
        p.load(fis);
        String dclass = (String) p.get("driverClass");
        String url = (String) p.get ("jdbcUrl");
        String username = (String) p.get ("user");
        String password = (String) p.get ("password");
        Class.forName(dclass);
        Connection con = DriverManager.getConnection(url, username, password);
        return con;
    }
}
