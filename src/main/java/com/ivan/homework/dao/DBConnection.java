package com.ivan.homework.dao;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String driver;
    private String url;
    private String name;
    private String password;

    public DBConnection() throws SQLException {
        Properties props = new Properties();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")){
            props.load(in);
        } catch (IOException ex) {
            throw new RuntimeException("incorrect properties file");
        }

        this.driver = props.getProperty("driverClass");
        this.url = props.getProperty("jdbcUrl");
        this.name = props.getProperty("user");
        this.password = props.getProperty("password");
    }

    public Connection getConnection() {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, name, password);
            Reader reader = Files.newBufferedReader(Paths.get("D:\\Java\\intensive\\second-homework\\src\\main\\resources\\db.sql"));
            ScriptRunner sr = new ScriptRunner(conn);
            sr.runScript(reader);
            System.out.println("Connection success");
            return conn;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("problem with connect to db");
        } catch (IOException ex) {
            System.out.println(ex);
            throw new RuntimeException("Migrations file not found");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found");
        }
    }
}
