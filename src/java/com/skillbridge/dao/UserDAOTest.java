package com.skillbridge.dao;

import com.skillbridge.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        User user = new User(
                "Test Freelancer",
                "test@skillbridge.com",
                "test123",
                "FREELANCER"
        );

        UserDAO userDAO = new UserDAO();

        boolean result =
                userDAO.registerUser(user);

        if (result) {

            System.out.println(
                "USER INSERTED SUCCESSFULLY!"
            );

        } else {

            System.out.println(
                "USER INSERT FAILED!"
            );
        }
    }
}