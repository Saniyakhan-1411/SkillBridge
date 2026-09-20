package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/MarkAllNotificationsReadServlet")
public class MarkAllNotificationsReadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
            request.getSession(false);

        if (session == null ||
            session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );

            return;
        }

        User loggedInUser =
            (User) session.getAttribute("loggedInUser");

        NotificationDAO dao =
            new NotificationDAO();

        dao.markAllAsRead(
            loggedInUser.getUserId()
        );

        response.sendRedirect(
            request.getContextPath()
            + "/NotificationServlet"
        );
    }
}