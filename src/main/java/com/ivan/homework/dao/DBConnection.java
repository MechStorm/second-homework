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
    private String dclass;
    private String url;
    private String username;
    private String password;

    public DBConnection() throws IOException {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            props.load(in);
        } catch (Exception ex) {
            throw new RuntimeException("Error with loading properties file");
        }

        this.dclass = props.getProperty("driverClass");
        this.url = props.getProperty("jdbcUrl");
        this.username = props.getProperty("user");
        this.password = props.getProperty("password");
    }

    public void connectDriver() {
        try {
            Class.forName(dclass);
        } catch (Exception ex) {
            throw new RuntimeException("Problem with linking driver");
        }
    }

    public Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            Reader reader = Files.newBufferedReader(Paths.get("db.sql"));
            ScriptRunner sr = new ScriptRunner(conn);
            sr.runScript(reader);
            System.out.println("Connection succeeded");
            return conn;
        } catch (Exception ex) {
            System.out.println("Connection failed");
            ex.printStackTrace();
        }

        return conn;
    }
}
