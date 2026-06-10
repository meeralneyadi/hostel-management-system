package com.hms;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC",
            "root",
            "root"
        );

        return con;
    }
}
