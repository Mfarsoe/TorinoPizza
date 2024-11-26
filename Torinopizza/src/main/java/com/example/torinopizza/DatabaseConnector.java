package com.example.torinopizza;

import java.sql.*;

public class DatabaseConnector {
    //Database Connection Infomationer
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/torinopizza";
    private static final String USER = "root";
    private static final String PASS = "rootPW";
    //private static final String connectionString = "jdbc:mysql://" + DB_URL + "?user=" + USER + "&password=" + PASS + "&useUnicode=true&characterEncoding=UTF-8";

    //Skaber connection og returnere en connection til nem brug andre steder.
    public static Connection connection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            //Trying to establish connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Error handling that prints a stacktrace for debugging
        } catch (Exception e) {
            System.out.println("Could not connect to database: TorinoPizza!\n");
            System.out.println(e.getMessage() + "\n STACK TRACE: \n");
            e.printStackTrace();
        }
        return conn;
    }
}

