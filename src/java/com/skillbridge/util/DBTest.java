package com.skillbridge.util;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        Connection connection = null;

        try {

            System.out.println(
                "Connecting to SkillBridge database..."
            );

            connection = DBConnection.getConnection();

            if (connection != null) {

                System.out.println(
                    "================================"
                );

                System.out.println(
                    "DATABASE CONNECTED SUCCESSFULLY!"
                );

                System.out.println(
                    "Database: skillbridge_db"
                );

                System.out.println(
                    "Driver: Apache Derby"
                );

                System.out.println(
                    "================================"
                );
            }

        } catch (Exception e) {

            System.out.println(
                "DATABASE CONNECTION FAILED!"
            );

            e.printStackTrace();

        } finally {

            if (connection != null) {

                try {
                    connection.close();

                    System.out.println(
                        "Database connection closed."
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}