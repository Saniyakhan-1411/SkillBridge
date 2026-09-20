package com.skillbridge.controller;

import com.skillbridge.dao.UserDAO;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Get form values
        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        // Remove spaces
        if (email != null) {
            email = email.trim().toLowerCase();
        }


        // ==============================
        // VALIDATION
        // ==============================

        if (email == null || email.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter your email address."
            );

            request.getRequestDispatcher(
                    "login.jsp"
            ).forward(request, response);

            return;
        }


        if (password == null || password.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter your password."
            );

            request.getRequestDispatcher(
                    "login.jsp"
            ).forward(request, response);

            return;
        }


        // ==============================
        // DATABASE LOGIN
        // ==============================

        UserDAO userDAO = new UserDAO();

        User user =
                userDAO.loginUser(
                        email,
                        password
                );


        // ==============================
        // LOGIN SUCCESS
        // ==============================

        if (user != null) {

            HttpSession session =
                    request.getSession();
            session.setMaxInactiveInterval(
        30 * 60
);

            // Store logged-in user
            session.setAttribute(
                    "loggedInUser",
                    user
            );

            session.setAttribute(
                    "userId",
                    user.getUserId()
            );

            session.setAttribute(
                    "userName",
                    user.getFullName()
            );

            session.setAttribute(
                    "role",
                    user.getRole()
            );


            // ==============================
            // ROLE BASED REDIRECTION
            // ==============================

            if ("CLIENT".equals(user.getRole())) {

                response.sendRedirect(
                        "client/dashboard.jsp"
                );

            }

            else if ("FREELANCER".equals(user.getRole())) {

                response.sendRedirect(
                        "freelancer/dashboard.jsp"
                );

            }

            else {

                session.invalidate();

                request.setAttribute(
                        "error",
                        "Invalid account role."
                );

                request.getRequestDispatcher(
                        "login.jsp"
                ).forward(request, response);
            }

        }

        // ==============================
        // LOGIN FAILED
        // ==============================

        else {

            request.setAttribute(
                    "error",
                    "Invalid email or password."
            );

            request.getRequestDispatcher(
                    "login.jsp"
            ).forward(request, response);
        }
    }
}