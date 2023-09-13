package com.ivan.homework.util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String driver;
    private String url;
    private String name;
    private String password;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


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

    public void driverLink() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Problem with driver connection");
        }
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, name, password);
            String dbFile = "com/ivan/homework/dao/db.sql";
            InputStream in = getClass().getClassLoader().getResourceAsStream(dbFile);
            InputStreamReader reader = new InputStreamReader(in);
            ScriptRunner sr = new ScriptRunner(conn);
            sr.runScript(reader);
            System.out.println("Connection success");
            return conn;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("problem with connect to db");
        }
    }
}
