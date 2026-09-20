package com.skillbridge.controller;

import com.skillbridge.dao.UserDAO;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Get form values
        String fullName =
                request.getParameter("fullName");

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        String role =
                request.getParameter("role");


        // Remove unnecessary spaces
        if (fullName != null) {
            fullName = fullName.trim();
        }

        if (email != null) {
            email = email.trim().toLowerCase();
        }


        // ==============================
        // VALIDATION
        // ==============================

        if (fullName == null ||
                fullName.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter your full name."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        if (email == null ||
                email.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter your email address."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        if (password == null ||
                password.length() < 6) {

            request.setAttribute(
                    "error",
                    "Password must contain at least 6 characters."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        if (!password.equals(confirmPassword)) {

            request.setAttribute(
                    "error",
                    "Passwords do not match."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        if (!"CLIENT".equals(role)
                && !"FREELANCER".equals(role)) {

            request.setAttribute(
                    "error",
                    "Please select an account type."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        // ==============================
        // DATABASE CHECK
        // ==============================

        UserDAO userDAO = new UserDAO();

        if (userDAO.emailExists(email)) {

            request.setAttribute(
                    "error",
                    "An account with this email already exists."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);

            return;
        }


        // ==============================
        // CREATE USER OBJECT
        // ==============================

        User user = new User(
                fullName,
                email,
                password,
                role
        );


        // ==============================
        // INSERT USER
        // ==============================

        boolean registered =
                userDAO.registerUser(user);


        // ==============================
        // RESULT
        // ==============================

        if (registered) {

            response.sendRedirect(
                    "login.jsp?registered=true"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Registration failed. Please try again."
            );

            request.getRequestDispatcher(
                    "register.jsp"
            ).forward(request, response);
        }
    }
}