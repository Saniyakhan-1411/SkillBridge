package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.model.Notification;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );

            return;
        }

        User loggedInUser =
            (User) session.getAttribute("loggedInUser");

        NotificationDAO notificationDAO =
            new NotificationDAO();

        List<Notification> notifications =
            notificationDAO.getNotificationsByUser(
                loggedInUser.getUserId()
            );

        int unreadCount =
            notificationDAO.getUnreadCount(
                loggedInUser.getUserId()
            );

        request.setAttribute(
            "notifications",
            notifications
        );

        request.setAttribute(
            "unreadCount",
            unreadCount
        );

        request.getRequestDispatcher(
            "/notifications.jsp"
        ).forward(request, response);
    }
}