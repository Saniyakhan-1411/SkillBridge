package com.skillbridge.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:derby://localhost:1527/skillbridge_db";

    private static final String USER =
            "saniya";

    private static final String PASSWORD =
            "s12345";

    static {
        try {

            Class.forName(
                "org.apache.derby.jdbc.ClientDriver"
            );

            System.out.println(
                "Derby Driver Loaded Successfully!"
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                "Derby Driver Not Found!"
            );

            e.printStackTrace();
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}