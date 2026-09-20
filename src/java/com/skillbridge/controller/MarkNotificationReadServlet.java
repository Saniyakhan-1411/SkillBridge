package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/MarkNotificationReadServlet")
public class MarkNotificationReadServlet extends HttpServlet {

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

        String id =
            request.getParameter("notificationId");

        try {

            int notificationId =
                Integer.parseInt(id);

            NotificationDAO dao =
                new NotificationDAO();

            dao.markAsRead(
                notificationId,
                loggedInUser.getUserId()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(
            request.getContextPath()
            + "/NotificationServlet"
        );
    }
}