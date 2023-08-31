package com.ivan.homework.dao;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        String aSQLScriptFilePath = "D:\\Java\\intensive\\second-homework\\src\\main\\java\\com\\ivan\\homework\\db\\db.sql";
        FileInputStream fis = new FileInputStream("D:\\Java\\intensive\\second-homework\\src\\main\\resources\\application.properties");
        Properties p = new Properties();
        p.load(fis);
        String dclass = (String) p.get("driverClass");
        String url = (String) p.get ("jdbcUrl");
        String username = (String) p.get ("user");
        String password = (String) p.get ("password");
        Class.forName(dclass);
        Connection con = DriverManager.getConnection(url, username, password);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));
        sr.runScript(reader);
        return con;
    }
}
